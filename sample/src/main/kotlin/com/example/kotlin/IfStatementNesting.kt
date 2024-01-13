package com.example.kotlin

class IfStatementNesting<E>(val elements: List<E>?) {
    fun foo() {
        if (true) {
            return
        }
        bar()
        baz()
    }

    fun bar() {}

    fun baz() {}
}
