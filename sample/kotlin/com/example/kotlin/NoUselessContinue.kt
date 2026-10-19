package com.example.kotlin

class NoUselessContinue {
    fun foo(things: List<String>) {
        for (thing in things) {
            println(thing)
            // continue
        }
    }
}
