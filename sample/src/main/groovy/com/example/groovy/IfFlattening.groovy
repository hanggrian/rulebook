package com.example.groovy

class IfFlattening<E> {
    void foo(boolean condition) {
        if (!condition) {
            return
        }
        println()
        println()
    }
}
