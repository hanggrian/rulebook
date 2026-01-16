package com.example.groovy

class EmptyBracesClip {
    def foo(var bar) {
        if (bar) { }
        if (!bar) {
        }
    }

    def bar() {
        while (true) {}
        do {
        } while (false)

        [].collect {}
    }
}
