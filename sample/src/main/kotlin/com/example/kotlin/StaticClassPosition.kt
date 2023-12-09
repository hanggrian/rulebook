package com.example.kotlin

class StaticClassPosition {
    class Foo(a: Int) {
        constructor() : this(VALUE)

        companion object {
            const val VALUE = 0
        }
    }
}
