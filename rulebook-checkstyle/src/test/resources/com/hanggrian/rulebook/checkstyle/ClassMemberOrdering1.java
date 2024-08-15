package com.hanggrian.rulebook.checkstyle;

public class ClassMemberOrdering {
    public class Foo {
        public int bar = 0;

        public Foo() {
            this(0);
        }

        public Foo(int a) {}

        public void baz() {}
    }
}
