package com.example.groovy

class MemberSeparator {
    static class Foo {
        var qux = 0
        // Lorem ipsum.
        var bar = 0

        /** Lorem ipsum. */
        Foo() {
            super()
        }

        /**
         * Lorem
         * ipsum.
         */
        def baz() {}
    }
}
