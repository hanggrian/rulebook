[![CircleCI](https://img.shields.io/circleci/build/gh/hanggrian/rulebook)](https://app.circleci.com/pipelines/github/hanggrian/rulebook/)
[![Codecov](https://img.shields.io/codecov/c/gh/hanggrian/rulebook)](https://app.codecov.io/gh/hanggrian/rulebook/) \
[![Maven Central](https://img.shields.io/maven-central/v/com.hanggrian.rulebook/rulebook-ktlint)](https://repo1.maven.org/maven2/com/hanggrian/rulebook/rulebook-ktlint/)
[![OpenJDK](https://img.shields.io/badge/jdk-11%2B-informational)](https://openjdk.java.net/projects/jdk8/) \
[![Package Index](https://shields.io/pypi/v/rulebook-pylint)](https://pypi.org/project/rulebook-pylint/)
[![Package Index Test](https://shields.io/pypi/v/rulebook-pylint?label=testpypi&pypiBaseUrl=https://test.pypi.org)](https://test.pypi.org/project/rulebook-pylint/)
[![Python](https://img.shields.io/badge/python-3-informational)](https://www.python.org/download/releases/3.0/)

# Rulebook

Third-party rules for JVM and Python lint tools, meant to be used in conjunction
with official ones. Most of the rules are opinionated personal code styles.
However, some already exists in other linters, providing the same experience
across multiple languages.

Language | Linter | Variants
--- | --- | ---
Kotlin | [Ktlint](https://pinterest.github.io/ktlint/) | [Ktlint Official Style](https://pinterest.github.io/ktlint/1.0.1/rules/code-styles/)
Java | [Checkstyle](https://checkstyle.org/) | [Sun Style](https://checkstyle.sourceforge.io/sun_style.html) or [Google Java Style](https://google.github.io/styleguide/javaguide.html)
Groovy | [CodeNarc](https://codenarc.org/) | [Groovy Style](https://groovy-lang.org/style-guide.html)
Python | [Pylint](https://pylint.org/) | [Pylint Style](https://pylint.pycqa.org/en/latest/user_guide/configuration/all-options.html) or [Google Python Style](https://google.github.io/styleguide/pyguide.html)

[View all rules](https://github.com/hanggrian/rulebook/wiki/)

## Download

### Maven

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

### PyPI

```sh
pip install pylint regex rulebook-pylint
```

## Usage

### Ktlint

- Apply [Ktlint Integration](https://pinterest.github.io/ktlint/0.49.1/install/integrations/#custom-gradle-integration)
  to Gradle project.
- Using configuration `ktlint`, add this project as dependency.

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

- Apply [Checkstyle Gradle Plugin](https://docs.gradle.org/current/userguide/checkstyle_plugin.html).
- Using configuration `checkstyle`, add this project as dependency.
- Point to local config file or put in `/config/checkstyle/codenarc.xml`.

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

- Apply [CodeNarc Gradle Plugin](https://docs.gradle.org/current/userguide/codenarc_plugin.html).
- Using configuration `codenarc`, add this project as dependency.
- Point to local config file or put in `/config/codenarc/codenarc.xml`.

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

### Pylint

- Point config file to local `pylintrc`.

## First time installation

Presuming the IDE is *IntelliJ IDEA* or *PyCharm*, consider applying the linter
style to it.

### Ktlint

Explained in [Ktlint IntelliJ IDEA Configuration](https://pinterest.github.io/ktlint/0.49.1/rules/configuration-intellij-idea/),
using standard [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html)
is enough:

- In **File > Settings > Editor > Code Style > Kotlin**, set from
  **Kotlin style guide**.
- Append `kotlin.code.style=official` to root `gradle.properties`.

### Checkstyle

Explained in [Google Java Format](https://github.com/google/google-java-format/):

- In **File > Settings > Editor > Code Style > Java**, import
  [IntelliJ Java Google Style file](https://raw.githubusercontent.com/google/styleguide/gh-pages/intellij-java-google-style.xml).

### Pylint

Apply few changes in settings:

- In **File > Settings > Editor > Code Style > Python > Blank Lines**:
  - Set **After local imports** and **Before the first method** to **0**.
  - Set the rest to **1**.
