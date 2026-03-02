package com.example.kotlin

class UnnecessaryContinue {
    fun foo(things: List<String>) {
        for (thing in things) {
            println(thing)
            // continue
        }
    }
}
