package com.example.groovy

class CaseSeparator {
    def foo(int bar) {
        switch (bar) {
            case 0: println(bar)
            case 1:
                println(bar)
                break

            default: println()
        }
    }
}
