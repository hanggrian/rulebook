# Declaration Return Type

Kotlin supports function and property declaration without a return type. This return type is
pre-calculated during compile time.

To avoid any ambiguity, this rule disables that behavior. This rule affects:
- All expression functions.
- Properties on top-level and within class.

This rules ignore function/property with modifier:
- private
- internal
