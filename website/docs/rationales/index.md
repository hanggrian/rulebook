## Abstract class require abstract method

Abstract modifier in a class is unnecessary if the class has no abstract
methods.

```kotlin hl_lines="1"
abstract class Message {
    fun send() {
        // implementation
    }
}
```

## Avoid primitive names

When declaring string and primitive variables, it is tempting to use simple
names based on their type. In fact, they are usually recommendations given by
the IDE. But these names are not descriptive and hard to understand what their
purpose is.

```kotlin
val string = "Alice"

val int = 30
```

## Concise brackets, parentheses and tags

Brackets and parentheses without content should be wrapped in a single line.

```groovy
var emptyList = [
]
var emptyMap = [
  :
]
```

## Hide utility class instance

To prevent instantiating a utility class, put a final modifier on the class
and add a private constructor.

```java hl_lines="1-2"
final class Strings {
    private Strings() {}

    static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
```

## Move trailing elvis to the next line

In a multiline statement, the elvis operator should align with chained method
calls instead of the same column.

```kotlin hl_lines="4"
val bananas =
    fruits
        .filter { it.type == BANANA }
        .takeUnless { it.isEmpty() } ?: return
```

## No blank lines at initial, final and consecutive comments

Comment and block comment bodies should not have leading or trailing blank
lines. No consecutive blank lines are allowed in the comment body.

```kotlin hl_lines="2 4 9"
/**
 *
 * The main function.
 *
 */
fun main() {
    // initialize the logger
    //
    //
    // and print the starting message
    val logger = Logger()
    logger.info("Starting the application")
}
```

## Remove empty file

Content of a file should not be empty or consist of only whitespace characters,
which may be spaces, tabs or newlines.

```kotlin hl_lines="1"

```

## Remove unused imports

Remove imports that are not used in the file.

```kotlin hl_lines="1"
import java.lang.String

val names = arrayListOf<String>()
```

## Trim multiline brackets, parentheses and tags

Method declaration, calls and collection initializers should not start or end
with a blank line.

```groovy hl_lines="2 11"
def getColors(

    int red,
    int green,
    int blue
) {
    return [
        red,
        green,
        blue,

    ]
}
```
