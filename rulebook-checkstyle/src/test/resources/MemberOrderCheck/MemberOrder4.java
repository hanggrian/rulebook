package com.hanggrian.rulebook.checkstyle;

class MemberOrder {
    class Foo {
        static void bar() {}

        static int baz = 0;

        Foo() {}
    }
}
