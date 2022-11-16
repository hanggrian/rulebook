# Exception Ambiguity

Throwing an exception requires one of two things:
- Supertype `Exception`, `Error`, and `Throwable` need a message. Preferably in form of sentence,
  although not enforced.
- Subtype such as `IllegalStateExeption`, `StackOverflowError`, etc. are explicit enough. Therefore,
  can have no message.

```kotlin
throw Exception() // violation
error("No data") // success
throw StackOverflowError() // success
```
