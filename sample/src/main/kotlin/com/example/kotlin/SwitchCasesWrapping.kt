package com.example.kotlin

class SwitchCasesWrapping(foo: Boolean, bar: () -> Unit, baz: () -> Unit) {
    init {
        when (foo) {
            true -> bar()
            false -> {
                baz()
            }
        }
    }
}
