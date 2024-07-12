package com.example.kotlin

class SwitchMultipleBranching(foo: Int, bar: () -> Unit) {
    init {
        if (true) bar()
        if (foo == 0) bar()
    }
}
