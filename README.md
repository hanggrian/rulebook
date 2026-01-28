[![CircleCI](https://img.shields.io/circleci/build/gh/hanggrian/rulebook)](https://app.circleci.com/pipelines/github/hanggrian/rulebook/)
[![Codecov](https://img.shields.io/codecov/c/gh/hanggrian/rulebook)](https://app.codecov.io/gh/hanggrian/rulebook/) \
[![Maven Central](https://img.shields.io/maven-central/v/com.hanggrian.rulebook/rulebook-ktlint)](https://repo1.maven.org/maven2/com/hanggrian/rulebook/rulebook-ktlint/)
[![Java](https://img.shields.io/badge/java-8+-informational)](https://docs.oracle.com/javase/8/) \
[![Package Index](https://shields.io/pypi/v/rulebook-pylint)](https://pypi.org/project/rulebook-pylint/)
[![Package Index Test](https://shields.io/pypi/v/rulebook-pylint?label=testpypi&pypiBaseUrl=https://test.pypi.org)](https://test.pypi.org/project/rulebook-pylint/)
[![Python](https://img.shields.io/badge/python-3.10+-informational)](https://docs.python.org/3.10/)

# Rulebook

![The Rulebook logo.](https://github.com/hanggrian/rulebook/raw/assets/logo.svg)

Third-party rules for JVM and Python lint tools, meant to be used in conjunction
with official ones. Most of the rules are opinionated personal code styles.
However, some already exists in other linters, providing the same experience
across multiple languages.

Language | Linter | Variants
--- | --- | ---
Java | [Checkstyle](https://github.com/checkstyle/checkstyle/) | [Sun's Java Style](https://checkstyle.sourceforge.io/sun_style.html) or [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
Groovy | [CodeNarc](https://github.com/CodeNarc/CodeNarc/) | [Groovy Style Guide](https://groovy-lang.org/style-guide.html)
Kotlin | [Ktlint](https://github.com/pinterest/ktlint/) | [Ktlint Official Style](https://pinterest.github.io/ktlint/1.0.1/rules/code-styles/)
C/C++ | [Cppcheck](https://github.com/danmar/cppcheck/) | [Qt Coding Style](https://wiki.qt.io/Qt_Coding_Style) or [Google C++ Style Guide](https://google.github.io/styleguide/cppguide.html)
Python | [Pylint](https://github.com/pylint-dev/pylint/) | [Pylint Style](https://pylint.pycqa.org/en/latest/user_guide/configuration/all-options.html) or [Google Python Style Guide](https://google.github.io/styleguide/pyguide.html)
JavaScript | [ESLint](https://github.com/eslint/eslint/) | [Proxmox JavaScript Style Guide](https://pve.proxmox.com/wiki/Javascript_Style_Guide) or [Google JavaScript Style Guide](https://google.github.io/styleguide/jsguide.html)
TypeScript | [typescript-eslint](https://github.com/typescript-eslint/typescript-eslint/) | [Microsoft TypeScript Coding Guidelines](https://github.com/microsoft/TypeScript/wiki/Coding-guidelines) or [Google TypeScript Style Guide](https://google.github.io/styleguide/tsguide.html)

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
pip install pylint regex rulebook-pylint
```

### NPM

Coming soon.

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

### ESLint

- Create `.eslint.config.js`.

  ```js
  import rulebookEslint, { proxmoxJavaScriptStyleNamed } from 'rulebook-eslint';
  import rulebookTypescriptEslint, { microsoftTypeScriptStyleNamed } from 'rulebook-typescript-eslint';

  export default typescriptEslint.config(
      {
          files: ['**/*.{js,jsx}'],
          extends: [js.configs.recommended],
          plugins: {
              'rulebook': rulebookEslint,
          },
          rules: proxmoxJavaScriptStyleNamed('rulebook'),
      },
      {
          files: ['**/*.{ts,tsx}'],
          extends: [
              js.configs.recommended,
              ...typescriptEslint.configs.recommendedTypeChecked,
          ],
          plugins: {
              'rulebook': rulebookTypescriptEslint,
          },
          rules: microsoftTypeScriptStyleNamed('rulebook'),
      },
  );
  ```

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
