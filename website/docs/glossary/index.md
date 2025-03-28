This section hopes to clarify some ambiguous terms used in rule names.

### Declaration

Declaring that a class, function or property exists. The implementation details
are considered to be [definition](#definition).

### Definition

Define how a class, function or property is implemented. Because of this, a
definition is a more complete description than a [declaration](#declaration).

### Class

In this library, a class means a class-like structure, which can be:

- Class
- Interface
- Enum
- Java-specific constructs
    - Annotation
    - Record (14+)
    - Sealed interface (17+)
- Kotlin-specific constructs
    - Data class
    - Sealed class
    - Object

### Function

Also referred to as method.

### Identifier

A unique name given to a class, function, or variable.

### Property

Also referred to as field.

### Variable

A variable is a local-scoped property in most programming languages. However,
in this library, a variable refers to a property and parameter.
