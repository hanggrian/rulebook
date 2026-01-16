package com.example.kotlin

class ElvisWrap {
    fun foo() {
        "".takeUnless { it.isEmpty() } ?: return
    }

    fun bar() {
        ""
            .takeUnless { it.isEmpty() }
            ?: return
    }

    fun baz() {
        "".takeUnless {
            it.isEmpty()
        } ?: return
    }
}
