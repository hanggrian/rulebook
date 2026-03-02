package com.example.kotlin

class ComplicatedBooleanEquality {
    fun foo(foo: Boolean) {
        if (foo) {
            println()
        }
    }

    fun bar(bar: Boolean) {
        if (!(bar)) {
            println()
        }
    }
}
