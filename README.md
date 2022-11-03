[![Travis CI](https://img.shields.io/travis/com/hendraanggrian/codestyle-ktlint)](https://travis-ci.com/github/hendraanggrian/codestyle-ktlint/)
[![Codecov](https://img.shields.io/codecov/c/github/hendraanggrian/codestyle-ktlint)](https://codecov.io/gh/hendraanggrian/codestyle-ktlint/)
[![Maven Central](https://img.shields.io/maven-central/v/com.hendraanggrian.codestyle/ktlint)](https://search.maven.org/artifact/com.hendraanggrian.codestyle/ktlint/)
[![Nexus Snapshot](https://img.shields.io/nexus/s/com.hendraanggrian.codestyle/ktlint?server=https%3A%2F%2Fs01.oss.sonatype.org)](https://s01.oss.sonatype.org/content/repositories/snapshots/com/hendraanggrian/codestyle/ktlint/)
[![OpenJDK](https://img.shields.io/badge/jdk-1.8%2B-informational)](https://openjdk.java.net/projects/jdk8/)

# Ktlint Codestyle

Personal kotlin code convention enforced by ktlint.

## Download

```gradle
repositories {
    mavenCentral()
}

configurations {
    ktlint
}

dependencies {
    ktlint "com.hendraanggrian:codestyle:ktlint:$version"
}
```

## Usage

[Integrate ktlint](https://pinterest.github.io/ktlint/install/integrations/#custom-gradle-integration)
to Gradle project. Add this custom ruleset using configuration `ktlint` and it's done!

```gradle
repositories {
    mavenCentral()
}

configurations {
    ktlint
}

dependencies {
    ktlint("com.pinterest:ktlint:0.47.1") {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, getObjects().named(Bundling, Bundling.EXTERNAL))
        }
    }
    ktlint "com.hendraanggrian:codestyle:ktlint:$version"
}

task ktlint(type: JavaExec, group: "verification") {
    description = "Check Kotlin code style."
    classpath = configurations.ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args "src/**/*.kt"
    // see https://pinterest.github.io/ktlint/install/cli/#command-line-usage for more information
}
check.dependsOn ktlint

task ktlintFormat(type: JavaExec, group: "formatting") {
    description = "Fix Kotlin code style deviations."
    classpath = configurations.ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args "-F", "src/**/*.kt"
    // see https://pinterest.github.io/ktlint/install/cli/#command-line-usage for more information
}
```
