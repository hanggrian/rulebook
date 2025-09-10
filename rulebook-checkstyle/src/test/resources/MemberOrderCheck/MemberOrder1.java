package com.hanggrian.rulebook.checkstyle.checks;

class MemberOrder {
    class Foo {
        int bar = 0;

        Foo() {
            this(0);
        }

        Foo(int a) {}

        void baz() {}
    }
}
