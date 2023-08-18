# Developing

Listed here are commands to print source files tree nodes for testing purposes.

```shell
ktlint --color printAST $file

checkstyle -T $file
checkstyle -J $file # for javadoc
```
