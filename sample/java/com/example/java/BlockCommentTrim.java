package com.example.java;

import java.util.List;

public class BlockCommentTrim {
    /**
     * Foo is awesome.
     *
     * @return a number.
     */
    public int foo() {
        return 0; // asd
    }

    /**
     * @return a number.
     */
    public int  bar() {
        return 0;
    }

    /**
     * @param t     a.
     * @param items b.
     */
    public <T> void baz(T t, List<T> items) {}
}
