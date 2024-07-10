package com.example.kotlin

class ConditionalBranchLineJoining(foo: Int, bar: () -> Unit, baz: () -> Unit) {
    init {
        when (foo) {
            0 -> bar()
            1 -> {
                baz()
            }
        }
    }
}
