package com.example.kotlin

class IfStatementNesting<E>(val elements: List<E>?) {
    fun iterate() {
        if (elements == null) {
            return
        }
        for (element in elements) {
            // ...
        }
    }
}
