package com.example.kotlin

class InnerClassPosition {
    class Foo(a: Int) {
        constructor() : this(0)

        data object A {
            override fun toString(): String = ""
        }
    }
}
