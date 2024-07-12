package com.example.groovy

class ElseFlattening<E> {
    void foo(boolean condition) {
        if (condition) {
            throw new IllegalStateException()
        }
        println()
        println()
    }
}
