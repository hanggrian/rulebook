package com.hanggrian.rulebook.checkstyle;

public class GenericsNameAllowing {
    public class Foo<T> {
        public class Bar<X> {}

        public <Y> void bar() {}
    }
}
