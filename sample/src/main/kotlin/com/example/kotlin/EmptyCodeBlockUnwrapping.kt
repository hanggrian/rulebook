package com.example.kotlin

class EmptyCodeBlockUnwrapping {
    fun foo() {}

    fun bar() {
        if (true) {
        } else if (false) {
        } else {
        }
    }

    class Baz(val baz: (Int) -> Unit = {})
}
