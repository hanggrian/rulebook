# Developing

Custom rules for each linter are implemented differently based on the tool capabilities and the limitations of the underlying language parser. Below are the details on which classes to extend for each linter.

```mermaid
graph LR
  A[<i>Checkstyle</i>] --> B{Is the rule<br>about Javadoc?};
  B --> |No| C[Is it achievable<br>using AST?];
  C --> |No| D[<b>AbstractFileSetCheck</b>];
  C --> |Yes| E[<b>AbstractCheck</b>];
  B ---> |Yes| F[<b>AbstractJavadocCheck</b>];
```

```mermaid
graph LR
  A[<i>CodeNarc</i>] --> B{Is the rule<br>about imports?};
  B --> |No| C[Is it achievable<br>using AST?];
  C --> |No| D[<b>AbstractRule</b>];
  C --> |Yes| E[<b>AbstractAstVisitorRule</b>];
  B ---> |Yes| F[<b>AbstractImportRule</b>];
```

```mermaid
graph LR
  A[<i>Ktlint</i>] --> B[<b>Rule</b> supports AST and raw file];
```

```mermaid
graph LR
  A[<i>Pylint</i>] --> B{Is it achievable<br>using AST?};
  B --> |No| C[Is it achievable<br>using tokens?];
  C --> |No| D[<b>BaseRawFileChecker</b>];
  C --> |Yes| E[<b>BaseTokenChecker</b>];
  B ---> |Yes| F[<b>BaseChecker</b>];
```

## Debugging

Print the AST tree of a file to help with debugging and development of rules.

### Ktlint

Print AST of a file.

```sh
ktlint --color printAST $file
```

> This command is no longer available in newer versions, use version [0.47.0](https://github.com/pinterest/ktlint/releases/tag/0.47.0).

### Checkstyle

Print AST of a file.

```sh
checkstyle -T $file
checkstyle -J $file # for javadoc
```

### Pylint

Use the following command to custom rules locally.

```sh
pip install -e rulebook
```

## Rule naming

Rule names are in form of state (missing-braces) or object (package-name). They are grouped by the intended objective (spacing) rather than the target site (imports). The rule messages that are printed to the console are in the form of verb phrase (Add missing braces or Rename package).

When possible, merge rules of relatable targets that achieve the same goal. For example, missing-if-braces and missing-for-braces can be merged into control-flow-bracing. Ignore this guideline if one of the rules is not applicable by the linter tool or in the language.
