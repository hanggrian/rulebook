[![CircleCI](https://img.shields.io/circleci/build/gh/hanggrian/rulebook)](https://app.circleci.com/pipelines/github/hanggrian/rulebook/)
[![Codecov](https://img.shields.io/codecov/c/gh/hanggrian/rulebook)](https://app.codecov.io/gh/hanggrian/rulebook/) \
[![Maven Central](https://img.shields.io/maven-central/v/com.hanggrian.rulebook/rulebook-ktlint)](https://repo1.maven.org/maven2/com/hanggrian/rulebook/rulebook-ktlint/)
[![Java](https://img.shields.io/badge/java-8+-informational)](https://docs.oracle.com/javase/8/) \
[![Package Index](https://shields.io/pypi/v/rulebook-pylint)](https://pypi.org/project/rulebook-pylint/)
[![Package Index Test](https://shields.io/pypi/v/rulebook-pylint?label=testpypi&pypiBaseUrl=https://test.pypi.org)](https://test.pypi.org/project/rulebook-pylint/)
[![Python](https://img.shields.io/badge/python-3+-informational)](https://docs.python.org/3/)

# Rulebook

![The Rulebook logo.](https://github.com/hanggrian/rulebook/raw/assets/logo.svg)

Third-party rules for JVM and Python lint tools, meant to be used in conjunction
with official ones. Most of the rules are opinionated personal code styles.
However, some already exists in other linters, providing the same experience
across multiple languages.

Language | Linter | Variants
--- | --- | ---
Kotlin | [Ktlint](https://pinterest.github.io/ktlint/) | [Ktlint Style](https://pinterest.github.io/ktlint/1.0.1/rules/code-styles/)
Java | [Checkstyle](https://checkstyle.org/) | [Sun Style](https://checkstyle.sourceforge.io/sun_style.html) or [Google Java Style](https://google.github.io/styleguide/javaguide.html)
Groovy | [CodeNarc](https://codenarc.org/) | [Groovy Style](https://groovy-lang.org/style-guide.html)
Python | [Pylint](https://pylint.org/) | [Pylint Style](https://pylint.pycqa.org/en/latest/user_guide/configuration/all-options.html) or [Google Python Style](https://google.github.io/styleguide/pyguide.html)

[View rules](https://hanggrian.github.io/rulebook/rules/)

## Download

### Maven

```gradle
repositories {
    mavenCentral()
}

dependencies {
    ktlint "com.hanggrian.rulebook:rulebook-ktlint:$version"
    checkstyle "com.hanggrian.rulebook:rulebook-checkstyle:$version"
    codenarc "com.hanggrian.rulebook:rulebook-codenarc:$version"
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
    ktlint "com.hanggrian.rulebook:rulebook-ktlint:$libraryVersion"
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
    checkstyle "com.hanggrian.rulebook:rulebook-checkstyle:$libraryVersion"
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
    codenarc "com.hanggrian.rulebook:rulebook-codenarc:$libraryVersion"
}
```

### Pylint

- Point config file to local `pylintrc`.
