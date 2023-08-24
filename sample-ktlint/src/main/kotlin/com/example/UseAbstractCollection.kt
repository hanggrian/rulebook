package com.example

fun listFunction(collection: List<String>) {
}

class SetClass(collection: Set<String>)

class TreeClass(num: Int) {
    constructor(num: Int, collection: Map<Int, String>) : this(num)
}

fun destructuring() {
    (0 to "Hi").let { (a: Int, b: String) ->
    }
}
