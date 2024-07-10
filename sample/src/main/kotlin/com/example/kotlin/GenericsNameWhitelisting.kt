package com.example.kotlin

class GenericsNameWhitelisting {
    fun <N> foo() {}

    class Bar<E>(a: E)
}
