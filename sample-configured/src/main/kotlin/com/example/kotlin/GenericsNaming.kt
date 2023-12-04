package com.example.kotlin

data class Point<A>(
    var x: A,
    var y: A,
)

inline fun <reified A> getTypeName() = A::class.java.simpleName
