[![Travis CI](https://img.shields.io/travis/com/hendraanggrian/lints)](https://travis-ci.com/github/hendraanggrian/lints/)
[![Codecov](https://img.shields.io/codecov/c/github/hendraanggrian/lints)](https://codecov.io/gh/hendraanggrian/lints/)
[![Maven Central](https://img.shields.io/maven-central/v/com.hendraanggrian.lints/lints-ktlint)](https://search.maven.org/artifact/com.hendraanggrian.lints/lints-ktlint/)
[![Nexus Snapshot](https://img.shields.io/nexus/s/com.hendraanggrian.lints/lints-ktlint?server=https%3A%2F%2Fs01.oss.sonatype.org)](https://s01.oss.sonatype.org/content/repositories/snapshots/com/hendraanggrian/lints/lints-ktlint/)
[![OpenJDK](https://img.shields.io/badge/jdk-1.8%2B-informational)](https://openjdk.java.net/projects/jdk8/)

# Lints

Additional third-party [rules](rules.md) for lint tools, meant to be used in conjunction with official ones.
Most of the rules are opinionated personal code style mandate. However, some already exists in other
linters, providing the same experience across languages.

| Language | Linter | Main Rules |
| --- | --- | --- |
| Java | Checkstyle | [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) via [Checkstyle Google Checks](https://checkstyle.sourceforge.io/google_style.html) |
| Kotlin | KtLint | [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html) via [KtLint standard rules](https://pinterest.github.io/ktlint/rules/standard/) |

## Download

```gradle
repositories {
    mavenCentral()
}

dependencies {
    checkstyle "com.hendraanggrian.lints:lints-checkstyle:$version"
    ktlint "com.hendraanggrian.lints:lints-ktlint:$version"
}
```

## Usage

### Checkstyle

Apply [Checkstyle Plugin](https://docs.gradle.org/current/userguide/checkstyle_plugin.html). Using
configuration `checkstyle`, add this project as dependency. Then use `lints_checks.xml` provided
in [sample-checkstyle](sample-checkstyle) or within [lints-checkstyle](lints-checkstyle) jar.

```gradle
plugins {
    checkstyle
}

checkstyle {
    toolVersion "$checkstyleVersion"
    configFile "path/to/lints_checks.xml"
    // the rest of checkstyle plugin configurations
}

dependencies {
    checkstyle "com.puppycrawl.tools:checkstyle:$checkstyleVersion"
    checkstyle "com.hendraanggrian.lints:lints-checkstyle:libraryVersion"
}
```

### KtLint

[Integrate ktlint](https://pinterest.github.io/ktlint/install/integrations/#custom-gradle-integration)
to Gradle project. Using configuration `ktlint`, add this project as dependency.

```gradle
configurations {
    ktlint
}

dependencies {
    ktlint "com.pinterest:ktlint:$ktlintVersion"
    ktlint "com.hendraanggrian.lints:lints-ktlint:libraryVersion"
}

// the rest of ktlint tasks' configuration
```
