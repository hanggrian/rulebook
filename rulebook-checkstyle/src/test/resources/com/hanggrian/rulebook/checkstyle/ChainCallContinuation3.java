package com.hanggrian.rulebook.checkstyle;

public class CaseLineSpacing {
    public void foo() {
        new Bar().baz()
            .baz
            .baz();
    }

    public static class Bar {
        public Bar bar = this;

        public Bar baz() {
            return this;
        }
    }
}
