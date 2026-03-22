package com.example.kotlin

class ComplicatedSizeEquality {
    fun foo(foo: List<Int>) {
        if (foo.isEmpty()) {
            println()
        }
        if (foo.isNotEmpty()) {
            println()
        }
    }

    fun bar(foo: Foo) {
        if (foo.qux().bar.isEmpty()) {
            println()
        }
    }

    class Foo(val bar: List<Int>) {
        fun qux(): Foo = this
    }
}
