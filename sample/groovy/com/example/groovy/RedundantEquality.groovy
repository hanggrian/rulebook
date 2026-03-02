package com.example.groovy

class RedundantEquality {
    def foo(Object foo) {
        if (foo.is(1) && !foo.is(2)) {
            println()
        }
    }
}
