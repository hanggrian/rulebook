package com.example.kotlin

class ExceptionInheritance {
    class A : Exception()

    class B : Exception {
        constructor()

        constructor(message: String)
    }
}
