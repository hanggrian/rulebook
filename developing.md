# Developing

## Ktlint

Print AST of a file.

```sh
ktlint --color printAST $file
```

> This command is no longer available in newer versions, use version [0.47.0](https://github.com/pinterest/ktlint/releases/tag/0.47.0).

## Checkstyle

Print AST of a file.

```sh
checkstyle -T $file
checkstyle -J $file # for javadoc
```

## Pylint

Use the following command to custom rules locally.

```sh
pip install -e rulebook
```
