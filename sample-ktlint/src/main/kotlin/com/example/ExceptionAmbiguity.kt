package com.example

fun throwCallReference() {
    // throw Exception()
    throw StackOverflowError()
}

fun throwReferenceExpression() {
    // val error = Exception()
    val error = StackOverflowError()
    throw error
}
