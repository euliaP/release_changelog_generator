# Kotlin Release Changelog Generator

This is a simple command-line tool that generates a changelog in Markdown format by fetching fixed issues for a specific release from the public Kotlin YouTrack project.

## Features

* Fetches all "Fixed" issues for a specified release version.
* Groups the issues by subsystem.
* Sorts the subsystems alphabetically.
* Generates a clean Markdown file named `changelog-<version>.md`.

---

## How to Use (Quickest Way)

This is the recommended way to run the tool without needing to build it yourself.

### Prerequisites

* You must have a **Java Runtime Environment (JRE)** installed (version 21 or higher).

### Linux / macOS Steps

1.  Go to the [**Releases**](https://github.com/euliaP/release_changelog_generator/releases) page of this repository.
2.  Download the latest `release_changelog.zip` file.
3.  Open your terminal, unzip the folder, and `cd` into it. Example:
    ```bash
    unzip release_changelog-1.0.zip
    cd release_changelog-1.0
    ```
4.  Make the launcher script executable (you only need to do this once):
    ```bash
    chmod +x ./bin/release_changelog
    ```
5.  Run the tool, passing your desired version:
    ```bash
    ./bin/release_changelog --version "2.1.20-Beta1"
    ```

---

## How to Build from Source (For Developers)

Use this method if you want to build the tool from the source code.

### Prerequisites

* You must have a **Java Development Kit (JDK)** installed (version 21 is recommended).

### Steps

1.  Clone this repository:
    ```bash
    git clone https://github.com/euliaP/release_changelog_generator.git
    cd release_changelog_generator
    ```
2.  Run the Gradle `installDist` task. This will build the script and place it in the `build/install/` directory.
    ```bash
    ./gradlew installDist
    ```
3.  Run the newly built script:
    ```bash
    ./build/install/release_changelog/bin/release_changelog --version "2.1.20-Beta1"
    ```

---

## Command-Line Usage

`Usage: changelog-generator`

| Option (Short) | Option (Long) | Description | Required |
| :--- | :--- | :--- | :--- |
| `-v` | `--version <String>` |  The Kotlin Project release version (e.g., 2.2.0, 2.3.0-Beta1) | **Yes** |
| `-h` | `--help` | Show this help message | No |

### Example

#### This command:
```bash
./bin/release_changelog --version "2.1.20-Beta1"
```

#### Will create this file:
[changelog-2.1.20-Beta1.md](https://github.com/euliaP/release_changelog_generator/blob/main/changelog-2.1.20-Beta1.md)