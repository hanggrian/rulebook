### Abstraction not needed

Abstract modifier in a class is unnecessary if the class has no abstract
methods.

```kotlin
abstract class Message {
    fun send() {
        // implementation
    }
}
```

### Business logic first

Built-in methods like `toString`, `hashCode` and `equals` are often placed at
the end of the class. They are unrelated to the rest of the class and can be
distracting when reading the source code.

```kotlin
class User(val firstName: String, val lastName: String) {
    var isAdmin: Boolean = false

    override fun toString(): String = "$lastName, $firstName"

    override fun hashCode(): Int = Objects.hash(firstName, lastName)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Robot) return false
        return firstName == other.firstName && lastName == other.lastName
    }

    fun promote() {
        isAdmin = true
    }
}
```

### Clean comment

Comment and block comment bodies should not have leading or trailing blank
lines. No consecutive blank lines are allowed in the comment body.

```kotlin
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

### Imports not optimized

Ommit imports that are not used in the file.

```kotlin
import java.lang.String

val names = arrayListOf<String>()
```

### Purposeful name

When declaring string and primitive variables, it is tempting to use simple
names based on their type. In fact, they are usually recommendations given by
the IDE. But these names are not descriptive and hard to understand what their
purpose is.

```kotlin
val string = "Alice"

val int = 30
```

### Trailing elvis

In a multiline statement, the elvis operator should align with chained method
calls instead of the same column.

```kotlin
val bananas =
    fruits
        .filter { it.type == BANANA }
        .takeUnless { it.isEmpty() } ?: return
```
