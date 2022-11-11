# Tag Description Punctuation

Description of certain tags is a sentence, therefore, must end with `.`, `?` or `!`.

```kotlin
/**
 * Add to something.
 *
 * @param x a non-negative number (violation)
 * @param y a non-negative number. (success)
 * @param z (success: no description)
 */
fun add(x: Number, y: Number, z: Number)
```

## Affected Tags

| Tag | Checkstyle | Ktlint |
| --- | :---: | :---: |
| @param | &check; | &check; |
| @return | &check; | &check; |
| @constructor | &cross; | &check; |
| @property | &cross; | &check; |
| @receiver | &cross; | &check; |
