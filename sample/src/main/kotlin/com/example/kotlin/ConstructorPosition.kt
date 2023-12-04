package com.example.kotlin

class ConstructorPosition(val name: String) {
    val nickname: String

    init {
        nickname = name.substring(0, 3)
    }

    constructor() : this("John Doe")

    fun greet() = print("Hi $nickname!")
}
