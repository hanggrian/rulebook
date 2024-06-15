package com.example.kotlin

class GenericsNameWhitelisting {
    fun <A> foo() {}

    class Bar<A>(a: A)
}
