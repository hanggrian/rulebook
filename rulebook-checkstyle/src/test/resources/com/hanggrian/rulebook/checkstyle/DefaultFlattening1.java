package com.hanggrian.rulebook.checkstyle;

public class DefaultFlattening {
    public void foo(int bar) {
        switch (bar) {
            case 0:
                baz();
                break;
            case 1:
                baz();
                break;
            default:
                baz();
                break;
        }
    }

    public void baz() {}
}
