# Function Specify Return Type

Kotlin supports `expression function` and `property` declaration without a return type. This return
type is pre-calculated during compile time. To avoid ambiguity, this rule forces them to specify
return type.

This rule doesn't affect:
- Functions without declaration.
- Properties in function block.
- Properties without getter.
- Non-public functions/properties.

```kotlin
fun topLevelFunction() = "Hello" // violation
fun anotherTopLevelFunction(): String = "World" // success

interface Vehicle {

    fun start() // success: declaration without block
}

class Car : Vehicle {
    val brand = "Toyota" // success
    val wheel get() = 4 // violation

    fun start() {
        var isStarted = false // success: declaration within block
    }
}

private class Bike : Vehicle {
    val brand = "Honda" // success: declaration within non-public body
}
```
