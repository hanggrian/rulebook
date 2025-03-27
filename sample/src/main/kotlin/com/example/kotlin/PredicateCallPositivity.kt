package com.example.kotlin

class PredicateCallPositivity {
    fun foo() {
        "".filter { it == null }
    }

    fun bar() {
        listOf("").takeUnless { it.isEmpty() }
    }

    fun baz() {
        listOf("").filterTo(mutableListOf()) { !it.isBlank() or it.isEmpty() }
    }
}
