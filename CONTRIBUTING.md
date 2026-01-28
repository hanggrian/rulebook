# Developing

Custom rules for each linter are implemented differently based on the tool capabilities and the limitations of the underlying language parser. Below are the details on which classes to extend for each linter.

```mermaid
graph LR
  A[<i>Checkstyle</i>] --> B{Is the rule<br>about Javadoc?};
  B --> |No| C[Is it achievable<br>using AST?];
  C --> |No| D[<b>AbstractFileSetCheck</b>];
  C --> |Yes| E[<b>AbstractCheck</b>];
  B --> |Yes| F[<b>AbstractJavadocCheck</b>];
```

```mermaid
graph LR
  A[<i>CodeNarc</i>] --> B{Is the rule<br>about imports?};
  B --> |No| C[Is it achievable<br>using AST?];
  C --> |No| D[<b>AbstractRule</b>];
  C --> |Yes| E[<b>AbstractAstVisitorRule</b>];
  B --> |Yes| F[<b>AbstractImportRule</b>];
```

```mermaid
graph LR
  A[<i>Ktlint</i>] --> B[<b>Rule</b> supports AST and raw file];
```

```mermaid
graph LR
  A[<i>Cppcheck</i>] --> B{Is it achievable<br>using AST?};
  B --> |No| C[Is it achievable<br>using tokens?];
  C --> |No| D[<b>Read raw file</b>];
  C --> |Yes| E[<b>Use tokens</b>];
  B --> |Yes| F[<b>Use scopes</b>];
```

```mermaid
graph LR
  A[<i>Pylint</i>] --> B{Is it achievable<br>using AST?};
  B --> |No| C[Is it achievable<br>using tokens?];
  C --> |No| D[<b>BaseRawFileChecker</b>];
  C --> |Yes| E[<b>BaseTokenChecker</b>];
  B --> |Yes| F[<b>BaseChecker</b>];
```

```mermaid
graph LR
  A[<i>ESLint</i>] --> B[<b>Rule</b> supports AST and source code];
```

## Configuration file

Rule message templates are stored in configuration files, distinct for each
source language.

Source language | Target language | File extension
--- | --- | ---
Kotlin | Java, Groovy, Kotlin | `.properties`
Python | C/C++, Python | `.cnf`
TypeScript | JavaScript, TypeScript | `.json`

## Debugging

Printing the AST tree to the console is possible with Checkstyle and Ktlint.

### "Ktlint"

> This command is no longer available in newer versions of Ktlint, use version
  [0.47.0](https://github.com/pinterest/ktlint/releases/tag/0.47.0).

```shell
ktlint --color printAST $file
```

### Checkstyle

```shell
checkstyle -T $file
checkstyle -J $file # for javadoc
```
