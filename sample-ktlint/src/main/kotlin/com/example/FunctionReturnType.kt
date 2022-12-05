package com.example

fun getAge1(): Int = 4

val age2: Int get() = 4

class Tester {
    @SomeTest
    fun test() = assert(true)
}

annotation class SomeTest
