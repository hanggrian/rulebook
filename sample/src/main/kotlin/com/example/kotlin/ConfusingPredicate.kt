package com.example.kotlin

class ConfusingPredicate {
    fun foo() {
        "".filter { it == null }
    }

    fun bar() {
        listOf("").takeUnless { it.isEmpty() }
    }

    fun baz() {
        1.takeUnless { it is Number }
    }
}
