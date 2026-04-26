[![GitHub Actions](https://img.shields.io/github/actions/workflow/status/hanggrian/rulebook/code-analysis.yaml)](https://github.com/hanggrian/rulebook/actions/workflows/code-analysis.yaml)
[![Codecov](https://img.shields.io/codecov/c/gh/hanggrian/rulebook)](https://app.codecov.io/gh/hanggrian/rulebook/)
[![Renovate](https://img.shields.io/badge/dependency-mend-blue)](https://developer.mend.io/github/hanggrian/rulebook/)\
[![Maven Central](https://img.shields.io/maven-central/v/com.hanggrian.rulebook/rulebook-ktlint)](https://repo1.maven.org/maven2/com/hanggrian/rulebook/rulebook-ktlint/)
[![Java](https://img.shields.io/badge/java-8+-informational)](https://docs.oracle.com/javase/8/)\
[![PyPI](https://shields.io/pypi/v/rulebook-pylint)](https://pypi.org/project/rulebook-pylint/)
[![TestPyPI](https://shields.io/pypi/v/rulebook-pylint?label=testpypi&pypiBaseUrl=https://test.pypi.org)](https://test.pypi.org/project/rulebook-pylint/)
[![Python](https://img.shields.io/badge/python-3.10+-informational)](https://docs.python.org/3.10/)\
[![NPM](https://shields.io/npm/v/rulebook-eslint)](https://www.npmjs.com/package/rulebook-eslint/)
[![Node](https://img.shields.io/badge/node-12+-informational)](https://nodejs.org/en/blog/release/v12.0.0/)

# Rulebook

![The Rulebook logo.](https://github.com/hanggrian/rulebook/raw/assets/logo.svg)

Third-party linter rules for multiple programming languages. It aims to
standardize existing rules from various linters, providing a consistent style
across different languages and tools. It also serves as a reference for best
practices in coding style.

| Language | Linter | Styles |
| --- | --- | --- |
| Java | [Checkstyle](https://github.com/checkstyle/checkstyle/) | [Sun Java Style](https://checkstyle.sourceforge.io/sun_style.html) or<br>[Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) |
| Groovy | [CodeNarc](https://github.com/CodeNarc/CodeNarc/) | [Groovy Style Guide](https://groovy-lang.org/style-guide.html) |
| Kotlin | [Ktlint](https://github.com/pinterest/ktlint/) | [Ktlint Official Style](https://pinterest.github.io/ktlint/1.0.1/rules/code-styles/) |
| C/C++ | [Cppcheck](https://github.com/danmar/cppcheck/) | [C++ Core Guidelines](https://isocpp.github.io/CppCoreGuidelines/CppCoreGuidelines) or<br>[Google C++ Style Guide](https://google.github.io/styleguide/cppguide.html) |
| Python | [Pylint](https://github.com/pylint-dev/pylint/) | [Pylint Style](https://pylint.pycqa.org/en/latest/user_guide/configuration/all-options.html) or<br>[Google Python Style Guide](https://google.github.io/styleguide/pyguide.html) |
| JavaScript | [ESLint](https://github.com/eslint/eslint/) | [Crockford Code Conventions](https://www.crockford.com/code.html) or<br>[Google JavaScript Style Guide](https://google.github.io/styleguide/jsguide.html) |
| TypeScript | [typescript-eslint](https://github.com/typescript-eslint/typescript-eslint/) | [Crockford Code Conventions](https://www.crockford.com/code.html) or<br>[Google TypeScript Style Guide](https://google.github.io/styleguide/tsguide.html) |

[View all rules](https://hanggrian.github.io/rulebook/rules/)

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
pip install rulebook-pylint
pip install rulebook-cppcheck
```

### NPM

```sh
npm install rulebook-eslint eslint globals \
  eslint-plugin-import \
  eslint-plugin-jsdoc \
  eslint-plugin-sort-class-members \
  eslint-vitest-rule-tester --save-dev
npm install rulebook-typescript-eslint typescript-eslint globals \
  eslint-plugin-import \
  eslint-plugin-jsdoc \
  eslint-plugin-sort-class-members \
  eslint-vitest-rule-tester --save-dev
```

## Usage

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

### Cppcheck

Create `addon.json` file.

### Pylint

Create `.pylintrc` file in the root directory.

### ESLint

Create `.eslint.config.js` file in the root directory.

## IDE settings

Presuming the IDE is *IntelliJ IDEA* or *PyCharm,* consider applying the linter
style to it.

1.  Ktlint Style

    Explained in [Ktlint IntelliJ IDEA Configuration](https://pinterest.github.io/ktlint/0.49.1/rules/configuration-intellij-idea/),
    using standard [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html)
    is enough:

    - In **File > Settings > Editor > Code Style > Kotlin**, set from
      **Kotlin style guide**.
      - Append `kotlin.code.style=official` to root `gradle.properties`.
1.  Google Java Style

    Explained in [Google Java Format](https://github.com/google/google-java-format/):

    - In **File > Settings > Editor > Code Style > Java**, import
      [IntelliJ Java Google Style file](https://raw.githubusercontent.com/google/styleguide/gh-pages/intellij-java-google-style.xml).
