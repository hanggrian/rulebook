[![Travis CI](https://img.shields.io/travis/com/hendraanggrian/lints)](https://travis-ci.com/github/hendraanggrian/lints/)
[![Codecov](https://img.shields.io/codecov/c/github/hendraanggrian/lints)](https://codecov.io/gh/hendraanggrian/lints/)
[![Maven Central](https://img.shields.io/maven-central/v/com.hendraanggrian.lints/lints-ktlint)](https://search.maven.org/artifact/com.hendraanggrian.lints/lints-ktlint/)
[![Nexus Snapshot](https://img.shields.io/nexus/s/com.hendraanggrian.lints/lints-ktlint?server=https%3A%2F%2Fs01.oss.sonatype.org)](https://s01.oss.sonatype.org/content/repositories/snapshots/com/hendraanggrian/lints/lints-ktlint/)
[![OpenJDK](https://img.shields.io/badge/jdk-1.8%2B-informational)](https://openjdk.java.net/projects/jdk8/)

# Lints

Personal Java/Kotlin linter rules. A highly opinionated additional code convention on top
of [official coding conventions](https://kotlinlang.org/docs/coding-conventions.html).

These rules are meant to be paired with base
rules ([Checkstyle Google Checks](https://checkstyle.sourceforge.io/google_style.html)
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
    ktlint "com.hendraanggrian.lints:lints-ktlint:$version"
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
    checkstyle "com.hendraanggrian.lints:lints-checkstyle:$version"
}
```

## Rules

| Rules | Checkstyle | Ktlint |
| --- | :---: | :---: |
| [Function Specify Return Type](guides/function-specify-return-type.md) | &cross; | &check; |

### Documentation Rules

| Rules | Checkstyle | Ktlint |
| --- | :---: | :---: |
| [Paragraph Continuation First Word](guides/docs/paragraph-continuation-first-word.md) | &check; | &check; |
| [Tag Description Punctuation](guides/docs/tag-description-punctuation.md) | &check; | &check; |
