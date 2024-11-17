package com.hanggrian.rulebook.checkstyle;

public class ClassMemberOrdering {
    public class Foo {
        public static void bar() {}

        public static int baz = 0;

        public Foo() {}
    }
}
