package com.example.kotlin

fun unnecessaryContinue(things: List<String>) {
    for (thing in things) {
        println(thing)
        // continue
    }
}
