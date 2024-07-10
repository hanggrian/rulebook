package com.example.kotlin

class ExceptionExtending {
    class A : Exception()

    class B : Exception {
        constructor()

        constructor(message: String)
    }
}
