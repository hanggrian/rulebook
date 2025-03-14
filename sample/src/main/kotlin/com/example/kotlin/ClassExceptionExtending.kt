package com.example.kotlin

class ClassExceptionExtending {
    class A : Exception()

    class B : Exception {
        constructor()

        constructor(message: String)
    }
}
