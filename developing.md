# Developing

Commands to print source files tree nodes for testing purposes.

## Ktlint

```shell
ktlint --color printAST $file
```

> This command is no longer available in newer versions, use version [0.47.0](https://github.com/pinterest/ktlint/releases/tag/0.47.0).

## Checkstyle

```shell
checkstyle -T $file
checkstyle -J $file # for javadoc
```
