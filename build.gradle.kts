plugins {
    kotlin("jvm") version "2.2.20"
    application
    kotlin("plugin.serialization") version "2.2.20"
}

group = "io.github.euliaP.release_changelog"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.ktor:ktor-client-core:3.3.2")
    implementation("io.ktor:ktor-client-cio:3.3.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("com.github.ajalt.clikt:clikt:5.0.1")
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
