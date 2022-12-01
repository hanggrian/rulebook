# Rules

| Rules | Checkstyle | KtLint |
| --- | :---: | :---: |
| [Exception ambiguity](#exception-ambiguity) | &check; | &check; |
| [Filename acronym](#filename-acronym) | `AbbreviationAsWordInName` | &check; |
| [Function return type](#function-return-type) | &cross; | &check; |
| [Type Kotlin API](#type-kotlin-api) | &cross; | &check; |

### Documentation Rules

| Rules | Checkstyle | KtLint |
| --- | :---: | :---: |
| [Summary continuation](#summary-continuation) | &check; | &check; |
| [Tag description sentence](#tag-description-sentence) | &check; | &check; |
| [Tags starting empty line](#tags-starting-empty-line) | `RequireEmptyLineBeforeBlockTagGroup` | &check; |

## Exception ambiguity

Throwing `Exception`, `Error`, and `Throwable` require a detail message. Does not apply to their
subtypes (`IllegalStateExeption`, `StackOverflowError`, etc.) as they are explicit enough.

```kotlin
throw Exception()
```

## Filename acronym

While uppercase acronym does comply with pascal-case naming standards, lowercase acronym is easier
to read. However, only 3 connecting uppercase letters are flagged.

```
RestAPI.kt
```

## Function return type

Prohibits declaration of public **expression function** and **property accessor** without return
type.

```kotlin
fun getMessage() = "Hello World"
val message get() = "Hello World"
```

## Type Kotlin API

Avoid using API from `java.*` and `org.junit.*` where there are Kotlin equivalents.

```kotlin
import java.lang.String
val images = java.util.ArrayList<Image>()
```

## Summary continuation

Every new line of continuation paragraph cannot start with **link** or **code**.

```kotlin
/**
 * This is a superclass of all
 * [cars] and
 * `bikes`.
 */
interface Vehicle
```

> This is a default behavior of markdown editor on *IntelliJ* IDEs.

## Tag description sentence

Description of certain tags, if present, is in form of a sentence. Therefore, must have one of end
punctuations `.`, `!`, or `?`.

```kotlin
/**
 * @param message a simple text
 */
fun send(message: String)
```

## Tags starting empty line

Both [Javadoc](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html)
and [Kdoc](https://kotlinlang.org/docs/kotlin-doc.html) recommend putting an empty space separating
summary and tag group.

```kotlin
/**
 * Insert an item to this collection.
 * @param element item to add.
 * @return true if element is successfully added.
 */
fun add(element: E): Boolean
```
