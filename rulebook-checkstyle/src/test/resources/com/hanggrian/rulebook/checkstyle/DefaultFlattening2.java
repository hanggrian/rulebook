package com.hanggrian.rulebook.checkstyle;

public class DefaultFlattening {
    public void foo(int bar) {
        switch (bar) {
            case 0:
                throw new Exception();
            case 1:
                return;
            default:
                baz();
        }
    }

    public void baz() {}
}
