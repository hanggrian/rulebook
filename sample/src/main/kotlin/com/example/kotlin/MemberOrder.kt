package com.example.kotlin

class MemberOrder {
    class Foo(a: Int) {
        val bar = 0

        init {
            println(bar)
        }

        constructor() : this(0)

        fun baz() {}

        companion object
    }
}
