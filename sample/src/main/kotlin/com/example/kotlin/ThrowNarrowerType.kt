package com.example.kotlin

fun throwCallReference() {
    // throw Exception()
    throw StackOverflowError()
}

fun throwReferenceExpression() {
    // val error = Exception()
    val error = StackOverflowError()
    val a = "$error"
    throw error
}
