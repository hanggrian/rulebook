package com.hanggrian.rulebook.checkstyle;

class ChainCallWrap {
    void foo() {
        new StringBuilder(
            "Lorem ipsum"
        ).append(0)
            .append(1);
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
