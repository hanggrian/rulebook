package com.example.kotlin

class SwitchCaseJoining(foo: Int, bar: () -> Unit, baz: () -> Unit) {
    init {
        when (foo) {
            0 -> bar()
            1 -> {
                baz()
            }
        }
    }
}
