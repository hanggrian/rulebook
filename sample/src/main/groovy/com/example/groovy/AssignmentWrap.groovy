package com.example.groovy

class AssignmentWrap {
    def foo() {
        var bar =
            1 +
                2
        var bar2 = [
            a: '',
            baa: '',
        ]

        var baz = i -> {
            System.out.println(i)
            System.out.println(i)
        }

        var qux = i ->
            System.out.println(i)
    }
}
