package com.example.groovy

class ChainCallWrap {
    def foo() {
        new StringBuilder(
            'Lorem ipsum',
        ).append(0)
            .append(1)
    }

    def bar() {
        var bar =
            new Baz()
                .baz()
                .baz
                .baz()
    }

    def baz() {
        String.format(
            new StringBuilder(
                'Lorem ipsum',
            ).append(0)
                .append(1)
                .toString(),
            new Baz()
                .baz()
                .baz
                .baz(),
        )
    }

    static class Baz {
        Baz baz = this

        Baz baz() {
            return this
        }
    }
}
