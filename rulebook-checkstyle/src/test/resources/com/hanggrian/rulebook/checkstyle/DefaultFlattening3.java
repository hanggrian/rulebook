package com.hanggrian.rulebook.checkstyle;

public class DefaultFlattening {
    public void foo(int bar) {
        switch (bar) {
            case 0:
                return;
            case 1:
                baz();
            default:
                baz();
        }
    }

    public void baz() {}
}
