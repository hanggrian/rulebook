# Developing

## Ktlint

Print AST of a file.

```shell
ktlint --color printAST $file
```

> This command is no longer available in newer versions, use version [0.47.0](https://github.com/pinterest/ktlint/releases/tag/0.47.0).

## Checkstyle

Print AST of a file.

```shell
checkstyle -T $file
checkstyle -J $file # for javadoc
```

## Pylint

Use the following command to custom rules locally.

```shell
pip install -e rulebook-pylint
```
