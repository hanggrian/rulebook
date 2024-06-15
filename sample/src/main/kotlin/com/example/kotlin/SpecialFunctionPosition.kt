package com.example.kotlin

class SpecialFunctionPosition {
    class Foo(a: Int) {
        fun bar() {}

        override fun toString() = "baz"

        override fun hashCode() = 0

        override fun equals(other: Any?) = false
    }
}
