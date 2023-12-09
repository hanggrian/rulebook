package com.example.kotlin

class StringInterpolation(name: Any, children: Collection<*>) {
    init {
        println("$name has ${children.size} children")
    }
}
