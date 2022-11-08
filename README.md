[![Travis CI](https://img.shields.io/travis/com/hendraanggrian/codestyle)](https://travis-ci.com/github/hendraanggrian/codestyle/)
[![Codecov](https://img.shields.io/codecov/c/github/hendraanggrian/codestyle)](https://codecov.io/gh/hendraanggrian/codestyle/)
[![Maven Central](https://img.shields.io/maven-central/v/com.hendraanggrian.codestyle/ktlint)](https://search.maven.org/artifact/com.hendraanggrian.codestyle/ktlint/)
[![Nexus Snapshot](https://img.shields.io/nexus/s/com.hendraanggrian.codestyle/ktlint?server=https%3A%2F%2Fs01.oss.sonatype.org)](https://s01.oss.sonatype.org/content/repositories/snapshots/com/hendraanggrian/codestyle/ktlint/)
[![OpenJDK](https://img.shields.io/badge/jdk-1.8%2B-informational)](https://openjdk.java.net/projects/jdk8/)

# Codestyle

Personal Java/Kotlin code convention and rules. These are additional rules that are meant to be
paired with base rules
([Checkstyle Google Checks](https://checkstyle.sourceforge.io/google_style.html)
and [Ktlint Standard Rules](https://pinterest.github.io/ktlint/rules/standard/)).

## Download

### Ktlint

[Integrate ktlint](https://pinterest.github.io/ktlint/install/integrations/#custom-gradle-integration)
to Gradle project. Using configuration `ktlint`, add this project as dependency.

```gradle
repositories {
    mavenCentral()
}

configurations {
    ktlint
}

dependencies {
    ktlint "com.hendraanggrian.codestyle:codestyle-ktlint:$version"
}
```

### Checkstyle

Copy the directory of `config` in this repository.
Apply [Checkstyle Plugin](https://docs.gradle.org/current/userguide/checkstyle_plugin.html). Using
configuration `checkstyle`, add this project as dependency.

```gradle
plugins {
    checkstyle
}

dependencies {
    checkstyle "com.hendraanggrian.codestyle:codestyle-checkstyle:$version"
}
```

## Rules

| Rules | Checkstyle | Ktlint |
| --- | :---: | :---: |
| [Declaration Return Type](guides/specify-return-type.md) | &cross; | &check; |
| [Documentation Content](guides/documentation-paragraph.md) | &check; | &check; |
| [Documentation Tag Description](guides/documentation-tag-description.md) | &check; | &check; |
| [Specify Return Type](guides/specify-return-type.md) | &cross; | &check; |
