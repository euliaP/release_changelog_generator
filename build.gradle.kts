plugins {
    kotlin("jvm") version "2.2.20"
    application
    kotlin("plugin.serialization") version "2.2.20"
}

group = "io.github.euliaP.release_changelog"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.ktor:ktor-client-core:2.3.10")
    implementation("io.ktor:ktor-client-cio:2.3.10")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.6")
}

kotlin {
    jvmToolchain(21)
}
application {
    mainClass.set("io.github.euliaP.release_changelog.ChangelogGeneratorKt")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "io.github.euliaP.release_changelog.ChangelogGeneratorKt"
    }
}

val copyDependencies by tasks.registering(Copy::class) {
    from(configurations.runtimeClasspath)
    into(layout.buildDirectory.dir("libs"))
}
