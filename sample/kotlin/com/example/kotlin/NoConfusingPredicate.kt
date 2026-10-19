package com.example.kotlin

class NoConfusingPredicate {
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
