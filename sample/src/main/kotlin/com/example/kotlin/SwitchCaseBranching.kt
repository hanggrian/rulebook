package com.example.kotlin

class SwitchCaseBranching(foo: Int, bar: () -> Unit) {
    init {
        if (true) bar()
        if (foo == 0) bar()
    }
}
