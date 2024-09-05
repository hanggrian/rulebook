package com.example.groovy

class DefaultDenesting {
    void foo(int bar) {
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
