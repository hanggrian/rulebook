!!! note inline end
    This section hopes to clarify some ambiguous terms used in rule names and
    messages.

Rule names are in form of state (missing-braces) or object (package-name). They
are grouped by the intended objective (spacing) rather than the target site
(imports). The rule messages that are printed to the console are in the form of
verb phrase (Add missing braces or Rename package).

When possible, merge rules of relatable targets that achieve the same goal. For
example, missing-if-braces and missing-for-braces can be merged into
control-flow-bracing. Ignore this guideline if one of the rules is not
applicable by the linter tool or in the language.

## Components

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
    - Pattern (17+)
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

## Actions

### Add

Insert a new component to the codebase.

### Arrange

Reorganize the order of components.

### Omit

Exclude certain elements without altering the rest of the component.

### Put

Include certain elements in a component without altering the rest of the
component.

### Move

Change the position of a component.

### Remove

Take out the whole component from the codebase.
