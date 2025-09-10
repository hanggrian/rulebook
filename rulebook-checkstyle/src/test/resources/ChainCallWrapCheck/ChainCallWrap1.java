package com.hanggrian.rulebook.checkstyle.checks;

class ChainCallWrap {
    void foo() {
        new StringBuilder(
            "Lorem ipsum"
        ).append(1)
            .append(
                2
            );
    }

    void baz() {
        new Bar()
            .baz(
                new String("Lorem ipsum")
            ).baz();
    }

    static class Baz {
        Baz baz(String s) {
            return this;
        }

        Baz baz() {
            return this;
        }
    }
}
