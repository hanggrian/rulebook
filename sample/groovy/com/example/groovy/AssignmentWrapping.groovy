package com.example.groovy

class AssignmentWrapping {
    def foo() {
        var bar =
            1 +
                2
        var bar2 = [
            a: '',
            baa: '',
        ]

        var baz = i -> {
            println(i)
            println(i)
        }

        var qux = i ->
            println(i)
    }
}
