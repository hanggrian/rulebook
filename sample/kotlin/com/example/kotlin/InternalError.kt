package com.example.kotlin

class InternalError {
    class A : Exception()

    class B : Exception {
        constructor()

        constructor(message: String)
    }
}
