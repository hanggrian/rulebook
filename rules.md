# Rules

| Rules | Checkstyle | KtLint |
| --- | :--: | :--: |
| [All name acronym](#all-name-acronym) | `AbbreviationAsWordInName` | &check; |
| [Class body starting whitespace](#class-body-starting-whitespace) | &cross; | &check; |
| [Functions return type](#functions-return-type) | &cross; | &check; |
| [Throw exception ambiguity](#throw-exception-ambiguity) | &check; | &check; |
| [Types Kotlin API](#types-kotlin-api) | &cross; | &check; |

### Documentation Rules

| Rules | Checkstyle | KtLint |
| --- | :-: | :-: |
| [Summary continuation](#summary-continuation) | &check; | &check; |
| [Tag description sentence](#tag-description-sentence) | &check; | &check; |
| [Tags starting whitespace](#tags-starting-whitespace) | `RequireEmptyLineBeforeBlockTagGroup` | &check; |

## All name acronym

While uppercase acronym does comply with pascal-case naming standards, lowercase
acronym is easier to read. However, only 3 connecting uppercase letters are
flagged. This rule affects **property**, **function**, **class-alike** and
even **file**.

```
val userJSON = "{ user: \"Hendra Anggrian\" }"
fun blendARGB()
class RestAPI
SQLUtility.kt
```

## Class body starting whitespace

If a class has a content and its declaration is multiline, the first line of the
content should be an empty line. Otherwise, do not put empty line on single line
class declaration. This rule affects all class-like objects
(`class`, `interface`, etc.).

```kotlin
class User(
    username: String,
    password: String
) {
    override fun toString(): String = username
}
```

## Functions return type

Prohibits declaration of public **expression function** and
**property accessor** without return type.

```kotlin
fun getMessage() = "Hello World"
val message get() = "Hello World"
```

## Throw exception ambiguity

Throwing `Exception`, `Error`, and `Throwable` require a detail message. Does
not apply to their subtypes (`IllegalStateExeption`, `StackOverflowError`, etc.)
as they are explicit enough.

```kotlin
throw Exception()
```

## Types Kotlin API

Avoid using API from `java.*` and `org.junit.*` where there are Kotlin
equivalents.

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

Description of certain tags, if present, is in form of a sentence. Therefore,
must have one of end punctuations `.`, `!`, or `?`.

```kotlin
/**
 * @param message a simple text
 */
fun send(message: String)
```

## Tags starting whitespace

Both [Javadoc](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html)
and [Kdoc](https://kotlinlang.org/docs/kotlin-doc.html) recommend putting an
empty space separating summary and tag group.

```kotlin
/**
 * Insert an item to this collection.
 * @param element item to add.
 * @return true if element is successfully added.
 */
fun add(element: E): Boolean
```
