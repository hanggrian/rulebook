package com.hanggrian.rulebook.ktlint.internals

/** Returns the type name without generics and nullability. */
internal val String.qualifierName: String
    get() = substringBefore('<').substringBefore('?')
