# Developing

Commands to print source files tree nodes for testing purposes.

## Ktlint

```shell
ktlint --color printAST $file
```

## Checkstyle

```shell
checkstyle -T $file
checkstyle -J $file # for javadoc
```
