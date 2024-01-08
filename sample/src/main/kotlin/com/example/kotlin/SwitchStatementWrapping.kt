package com.example.kotlin

class SwitchStatementWrapping(foo: Boolean, bar: () -> Unit, baz: () -> Unit) {
    init {
        when (foo) {
            true -> bar()
            false -> {
                baz()
            }
        }
    }
}
