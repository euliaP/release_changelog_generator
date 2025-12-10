package io.github.euliaP.release_changelog

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import java.io.File
import kotlin.system.exitProcess

// A builder that holds custom JSON-parsing rules
val jsonParser = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
}

// Data schemas that are used for response mapping
@Serializable
data class CustomFieldValue(
    val name: String
)

@Serializable
data class CustomField(
    val name: String,
    val value: JsonElement? = null
)

@Serializable
data class Issue(
    val idReadable: String,
    val summary: String? = null,
    val customFields: List<CustomField> = emptyList()
)

// No serialization is needed as it'll be used for transforming of an object into a new object
data class ProcessedIssue(
    val id: String,
    val summary: String,
    val subsystem: String
)

// A helper function with additional logic for parsing "Subsystems" field
fun Issue.findSubsystem(): String {
    val subsystemField = this.customFields.find { it.name == "Subsystems" }

    return subsystemField?.value?.let { value ->
        if (value is JsonArray && value.isNotEmpty()) {
            try {
                jsonParser.decodeFromJsonElement<CustomFieldValue>(value[0]).name
            } catch (e: Exception) {
                println("Warning: Failed to parse subsystem for issue. ${e.message}")
                "Uncategorized" // Parsing failed
            }
        } else {
            "Uncategorized" // Not an array or empty
        }
    } ?: "Uncategorized" // 'subsystemField' or 'value' was null
}

class ChangelogCommand : CliktCommand(name = "changelog-generator") {
    private val releaseVersion by option(
        "-v", "--version",
        help = "The Kotlin Project release version (e.g., \\\"2.1.20-Beta1\\\")."
    ).required()
    override fun run() {
        /*
        Validating release version input based on Semantic Versioning:
        MAJOR.MINOR.PATCH-MODIFIER
        */
        val versionRegex = Regex("^\\d+\\.\\d+\\.\\d+(-[A-Za-z0-9-]+)?$")
        if (!versionRegex.matches(releaseVersion)) {
            echo("Error: '$releaseVersion' is not a valid version format.", err = true)
            echo(
                "Expected format: MAJOR.MINOR.PATCH (e.g., 2.1.20) or MAJOR.MINOR.PATCH-MODIFIER (e.g., 2.1.20-Beta1)",
                err = true
            )
            exitProcess(1)
        }
        echo("Input format appears valid. Fetching the data...")
        runBlocking {
            buildChangeLog(releaseVersion)
        }
    }
}

fun main(args: Array<String>) {
    ChangelogCommand().main(args)
}

fun buildChangeLog(releaseVersion: String) = runBlocking {

    val client = HttpClient(CIO)    // Connection pool manager; because we intend to send more than one request

    val query = "project: KT AND State: Fixed AND \"Available in\": $releaseVersion"
    val fields = "idReadable,summary,customFields(name,value(name))"

    val allIssues = mutableListOf<Issue>()
    var skip = 0
    val pageSize = 100
    var keepFetching = true

    println("Fetching issues for $releaseVersion...")

    // Fetching all the issues of the release version per page
    try {
        while (keepFetching) {
            println("Fetching page (skip $skip)...")
            // #TODO: possible improvement. add retry logic for temp server-side errors
            val response: HttpResponse = client.get("https://youtrack.jetbrains.com/api/issues") {
                url {
                    parameters.append("query", query)
                    parameters.append("fields", fields)
                    parameters.append("\$skip", "$skip")
                    parameters.append("\$top", "$pageSize")
                }
                headers {
                    append("Accept", "application/json")
                }
            }

            val responseBody = response.bodyAsText()
            val issuesOnThisPage: List<Issue> = jsonParser.decodeFromString(responseBody)

            println("...Page (skip $skip) parsed, found ${issuesOnThisPage.size} issues.")

            if (issuesOnThisPage.isEmpty()) {
                keepFetching = false
            } else {
                allIssues.addAll(issuesOnThisPage)
                skip += pageSize
            }
        }
        println("\nTotal issues fetched: ${allIssues.size}")

        if (allIssues.isEmpty()) {
            println("No issues found for version: $releaseVersion.")
            client.close()
            return@runBlocking
        }

        // Regrouping and formatting fetched issues data
        val processedIssues = allIssues.map { issue ->
            ProcessedIssue(
                id = issue.idReadable,
                summary = issue.summary ?: "(No Summary)",
                subsystem = issue.findSubsystem()
            )
        }

        val groupedIssues = processedIssues.groupBy { it.subsystem }
        val sortedGroups = groupedIssues.toSortedMap()

        // Building markdown
        println("\n--- GENERATING MARKDOWN ---")

        val markdownContent = buildString {
            appendLine("## $releaseVersion")
            appendLine() // Adding a blank line

            sortedGroups.forEach { (subsystem, issueList) ->
                appendLine("### $subsystem")

                issueList.forEach { issue ->
                    val link = "https://youtrack.jetbrains.com/issue/${issue.id}"
                    appendLine("- [${issue.id}]($link) ${issue.summary}")
                }
                appendLine() // Adding a blank line
            }
        }

        // Writing everything to a file. #TODO: possible optimization place. (don't store the whole list in memory before writing but write line by line)
        val fileName = "changelog-$releaseVersion.md"
        File(fileName).writeText(markdownContent)

        println("Success! Generated $fileName")

    } catch (e: Exception) {           // Catching connection/ DNS/ timeout/ API/ Parsing errors
        println("Error: ${e.message}")
        e.printStackTrace()            // Printing stack trace for debugging in case of error
    } finally {
        client.close()
    }
}