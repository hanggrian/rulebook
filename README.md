[![Travis CI](https://img.shields.io/travis/com/hendraanggrian/rulebook)](https://travis-ci.com/github/hendraanggrian/rulebook/)
[![Codecov](https://img.shields.io/codecov/c/github/hendraanggrian/rulebook)](https://codecov.io/gh/hendraanggrian/rulebook/)
[![Maven Central](https://img.shields.io/maven-central/v/com.hendraanggrian.rulebook/rulebook-ktlint)](https://repo1.maven.org/maven2/com/hendraanggrian/rulebook/rulebook-ktlint/)
[![Nexus Snapshot](https://img.shields.io/nexus/s/com.hendraanggrian.rulebook/rulebook-ktlint?server=https%3A%2F%2Fs01.oss.sonatype.org)](https://s01.oss.sonatype.org/content/repositories/snapshots/com/hendraanggrian/rulebook/rulebook-ktlint/)
[![OpenJDK](https://img.shields.io/badge/jdk-1.8%2B-informational)](https://openjdk.java.net/projects/jdk8/)

# Rulebook

[Third-party rules](https://github.com/hendraanggrian/rulebook/wiki/) for
JVM-based lint tools, meant to be used in  conjunction with official ones. Most
of the rules are opinionated personal code styles. However, some already exists
in other linters, providing the same experience across languages.

| Language | Linter | Main Rules |
| --- | --- | --- |
| Kotlin | [Ktlint](https://pinterest.github.io/ktlint/) | [Kotlin coding conventions] via [Ktlint Standard Rules](https://pinterest.github.io/ktlint/0.49.1/rules/standard/) |
| Java | [Checkstyle](https://checkstyle.org/) | [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) via [Checkstyle Google Checks](https://checkstyle.sourceforge.io/google_style.html) |
| Groovy | [CodeNarc](https://codenarc.org/) | [Groovy Style Guide](https://groovy-lang.org/style-guide.html) |

## Download

```gradle
repositories {
    mavenCentral()
}
dependencies {
    ktlint "com.hendraanggrian.rulebook:rulebook-ktlint:$version"
    checkstyle "com.hendraanggrian.rulebook:rulebook-checkstyle:$version"
    codenarc "com.hendraanggrian.rulebook:rulebook-codenarc:$version"
}
```

## Usage

### Ktlint

Apply [Ktlint Integration](https://pinterest.github.io/ktlint/0.49.1/install/integrations/#custom-gradle-integration)
to Gradle project. Using configuration `ktlint`, add this project as dependency.

```gradle
configurations {
    ktlint
}
dependencies {
    ktlint "com.hendraanggrian.rulebook:rulebook-ktlint:$libraryVersion"
}
// the rest of ktlint tasks' configuration
```

### Checkstyle

Apply [Checkstyle Gradle Plugin](https://docs.gradle.org/current/userguide/checkstyle_plugin.html).
Using configuration `checkstyle`, add this project as dependency. Then, point
config file to `rulebook_checkstyle.xml`.

```gradle
plugins {
    checkstyle
}
checkstyle {
    toolVersion "$checkstyleVersion"
    configFile "path/to/rulebook_checkstyle.xml"
}
dependencies {
    checkstyle "com.hendraanggrian.rulebook:rulebook-checkstyle:$libraryVersion"
}
```

### CodeNarc

Apply [CodeNarc Gradle Plugin](https://docs.gradle.org/current/userguide/codenarc_plugin.html).
Using configuration `codenarc`, add this project as dependency. Then, point
config file to `rulebook_codenarc.xml`.

```gradle
plugins {
    codenarc
}
codenarc {
    toolVersion "$codenarcVersion"
    configFile "path/to/rulebook_codenarc.xml"
}
dependencies {
    codenarc "com.hendraanggrian.rulebook:rulebook-codenarc:$libraryVersion"
}
```

## First time installation

Presuming the IDE is *IntelliJ IDEA*, consider applying the linter style to it.

### Ktlint

Explained in [Ktlint IntelliJ IDEA Configuration](https://pinterest.github.io/ktlint/0.49.1/rules/configuration-intellij-idea/),
using standard [Kotlin coding conventions] is enough:

- In `File > Settings > Editor > Code Style > Kotlin`, set from
  `Kotlin style guide`.
- Append `kotlin.code.style=official` to root `gradle.properties`.

[Kotlin coding conventions]: https://kotlinlang.org/docs/coding-conventions.html

### Checkstyle

In `File > Settings > Editor > Code Style > Java`, import
[IntelliJ Java Google Style file](https://raw.githubusercontent.com/google/styleguide/gh-pages/intellij-java-google-style.xml).
The whole process are better explained in [Google Java Format](https://github.com/google/google-java-format/).
