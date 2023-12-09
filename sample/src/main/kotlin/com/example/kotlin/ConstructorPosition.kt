package com.example.kotlin

class ConstructorPosition {
    class Foo(a: Int) {
        val bar = 0

        init {
            println(bar)
        }

        constructor() : this(0)

        fun baz() {}
    }
}
