package com.example.groovy

class DefaultFlattening {
    def foo(var bar) {
        switch (bar) {
            case 0:
                throw new IllegalStateException()
            case 1:
                return
        }
        println()
        println()
    }
}
