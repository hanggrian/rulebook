package com.example

data class Point<N>(
    var x: N,
    var y: N,
)

inline fun <reified T> getTypeName(): String = T::class.java.simpleName
