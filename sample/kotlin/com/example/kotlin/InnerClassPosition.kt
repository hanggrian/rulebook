package com.example.kotlin

object InnerClassPosition {
    class Foo(a: Int) {
        constructor() : this(0)

        data object A {
            override fun toString(): String = ""
        }

        interface B {
            val b: String
        }
    }
}
