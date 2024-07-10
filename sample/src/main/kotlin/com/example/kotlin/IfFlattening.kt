package com.example.kotlin

class IfFlattening {
    fun iterate(elements: List<Int>?) {
        if (elements == null) {
            return
        }
        for (element in elements) {
            // ...
        }
    }
}
