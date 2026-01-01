Presuming the IDE is *IntelliJ IDEA* or *PyCharm,* consider applying the linter
style to it.

## Ktlint Style

Explained in [Ktlint IntelliJ IDEA Configuration](https://pinterest.github.io/ktlint/0.49.1/rules/configuration-intellij-idea/),
using standard [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html)
is enough:

- In **File > Settings > Editor > Code Style > Kotlin**, set from
  **Kotlin style guide**.
- Append `kotlin.code.style=official` to root `gradle.properties`.

## Google Java Style

Explained in [Google Java Format](https://github.com/google/google-java-format/):

- In **File > Settings > Editor > Code Style > Java**, import
  [IntelliJ Java Google Style file](https://raw.githubusercontent.com/google/styleguide/gh-pages/intellij-java-google-style.xml).
