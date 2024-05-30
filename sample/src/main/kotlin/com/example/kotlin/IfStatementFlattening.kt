package com.example.kotlin

class IfStatementFlattening {
    fun iterate(elements: List<Int>?) {
        if (elements == null) {
            return
        }
        for (element in elements) {
            // ...
        }
    }
}
