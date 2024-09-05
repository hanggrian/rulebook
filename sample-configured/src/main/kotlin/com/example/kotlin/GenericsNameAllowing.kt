package com.example.kotlin

class GenericsNameAllowing {
    fun <A> foo() {}

    class Bar<A>(a: A)
}
