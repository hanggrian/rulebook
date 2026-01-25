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
JavaScript | [ESLint](https://eslint.org/) | [Proxmox JavaScript Style](https://pve.proxmox.com/wiki/Javascript_Style_Guide) or [Google JavaScript Style](https://google.github.io/styleguide/jsguide.html)
TypeScript | [typescript-eslint](https://typescript-eslint.io/) | [Microsoft TypeScript Style](https://github.com/microsoft/TypeScript/wiki/Coding-guidelines) or [Google TypeScript Style](https://google.github.io/styleguide/tsguide.html)

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
