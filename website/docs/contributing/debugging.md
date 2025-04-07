Printing the AST tree to the console is possible with Checkstyle and Ktlint.

!!! warning
    This command is no longer available in newer versions of Ktlint, use version [0.47.0](https://github.com/pinterest/ktlint/releases/tag/0.47.0).

=== "Ktlint"
    ```shell
    ktlint --color printAST $file
    ```
=== "Checkstyle"
    ```shell
    checkstyle -T $file
    checkstyle -J $file # for javadoc
    ```
