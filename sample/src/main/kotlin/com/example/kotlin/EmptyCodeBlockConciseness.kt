package com.example.kotlin

class EmptyCodeBlockConciseness {
    fun foo() {}

    class Bar(val baz: (Int) -> Unit = {})
}
