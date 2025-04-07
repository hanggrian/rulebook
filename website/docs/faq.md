## Why is it necessary?

When working on a project with multiple programming languages, we often forget
to apply the same coding style and leave the validation to a linter tool.
However, the default behavior of these linters are not always consistent.
Consider the example below:

<table>
  <thead>
    <tr>
      <th>Java</th>
      <th>Groovy</th>
      <th>Kotlin</th>
      <th>Python</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>
        Java does not allow trailing commas except in array initializers.
      </td>
      <td>
        Groovy allows trailing commas in call sites, but CodeNarc does not
        natively support it.
      </td>
      <td>
        Trailing commas can be placed in call and declaration sites in Kotlin,
        the rule is provided by Ktlint.
      </td>
      <td>
        Python allows trailing commas but Pylint considers it optional in PEP.
        <i>Note that the comment spacing rule is different in Python.</i>
      </td>
    </tr>
    <tr>
      <td>
```java
void foo(
    int a,
    int b
) {
    bar(
        a,
        b
    )
}
```
      </td>
      <td>
```groovy
def foo(
    int a,
    int b
) {
    bar(
        a,
        b // no!
    )
}
```
      </td>
      <td>
```kotlin
fun foo(
    a: Int,
    b: Int // no!
) =
    bar(
        a,
        b // no!
    )
```
      </td>
      <td>
        <pre>
```python
def foo(
    a: int,
    b: int  # no!
):
    bar(
        a,
        b  # no!
    )
```
      </td>
    </tr>
  </tbody>
</table>

## How stable is it?

The rules are mostly work in progress and have not been tested against a large
codebase. Disable the rules individually if they behave unexpectedly.

=== "checkstyle.xml"
    ```xml
    <!--module name="CommentSpaces"/-->
    ```
=== "codenarc.xml"
    ```xml
    <!--rule class="com.hanggrian.rulebook.codenarc.CommentSpacesRule"/-->
    ```
=== ".editorconfig"
    ```ini
    ktlint_rulebook_comment-spaces = disabled
    ```
=== "pylintrc"
    ```python
    # rulebook_pylint.comment_spaces,
    ```

## What's next for Rulebook?

Although there is no timeline for the roadmap, the following features are
planned:

- More languages:
    - [X] Java
    - [X] Groovy
    - [X] Kotlin
    - [X] Python
    - [ ] JavaScript
    - [ ] TypeScript
- Rigorous testing:
    - [X] Consistent unit test names and cases across all languages.
    - [ ] Collect interesting code snippets from open-source projects.
