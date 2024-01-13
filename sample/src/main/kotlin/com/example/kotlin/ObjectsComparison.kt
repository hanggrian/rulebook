package com.example.kotlin

class ObjectsComparison(foo: Any, bar: Any) {
    init {
        if (foo == bar) {
            baz()
        }
    }

    fun baz() {}
}
