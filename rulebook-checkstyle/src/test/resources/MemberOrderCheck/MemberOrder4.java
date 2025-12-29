package com.hanggrian.rulebook.checkstyle.checks;

class MemberOrder {
    class Foo {
        static void qux() {}

        void bar() {}
    }
}
