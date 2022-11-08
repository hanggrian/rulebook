# Specify Return Type

Kotlin supports `expression function` and `property` declaration without a return type. This return
type is pre-calculated during compile time. To avoid ambiguity, this rule forces them to specify
return type.

This rule doesn't affect:
- Functions without declaration.
- Properties in function block.
- Non-public functions/properties.
