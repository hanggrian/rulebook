package com.example.kotlin

class RequiredGenericName {
    fun <A> foo() {}

    class Bar<A>(a: A)
}
