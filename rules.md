# Rules

| Rules | Checkstyle | KtLint |
| --- | :---: | :---: |
| [Exception ambiguity](#exception-ambiguity) | &check; | &check; |
| [Function specify return type](#function-specify-return-type) | &cross; | &check; |

### Documentation Rules

| Rules | Checkstyle | KtLint |
| --- | :---: | :---: |
| [Summary continuation first word](#summary-continuation-first-word) | &check; | &check; |
| [Tag Description Sentence](#tag-description-sentence) | &check; | &check; |
| [Tag Group Starting Empty Line](#tag-group-starting-empty-line) | `RequireEmptyLineBeforeBlockTagGroup` | &check; |

## Exception ambiguity

Throwing `Exception`, `Error`, and `Throwable` require a detail message. Does not apply to their
subtypes (`IllegalStateExeption`, `StackOverflowError`, etc.) as they are explicit enough.

## Function specify return type

Prohibits declaration of public **expression function** and **property accessor** without return
type.

## Summary continuation first word

Every new line of continuation paragraph cannot start with **link** or **code**.

> This is a default behavior of markdown editor on *IntelliJ* IDEs.

## Tag description sentence

Description of certain tags, if present, is in form of a sentence. Therefore, must have one of end
punctuations `.`, `!`, or `?`.

## Tag group starting empty line

Both [Javadoc](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html)
and [Kdoc](https://kotlinlang.org/docs/kotlin-doc.html) recommend putting an empty space separating
summary and tag group.
