package com.example.kotlin

class EmptyBracesClip {
    fun foo() {}

    fun bar() {
        while (true) {}
        do {
        } while (false)
    }

    class Baz(val baz: (Int) -> Unit = {})
}
