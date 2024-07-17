package com.example.kotlin

class ElvisWrapping {
    fun foo() {
        "".takeIf { it.isNotEmpty() } ?: return
    }

    fun bar() {
        ""
            .takeIf { it.isNotEmpty() }
            ?: return
    }

    fun baz() {
        "".takeIf {
            it.isNotEmpty()
        } ?: return
    }
}
