package com.example.groovy

class BracesClip {
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

    def baz() {
        try {
        } catch (IOException e) {
        }
    }
}
