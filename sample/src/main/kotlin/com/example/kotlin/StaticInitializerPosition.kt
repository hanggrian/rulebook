package com.example.kotlin

class StaticInitializerPosition(val name: String) {
    constructor() : this(DEFAULT_NAME)

    companion object {
        const val DEFAULT_NAME = "John Doe"
    }
}
