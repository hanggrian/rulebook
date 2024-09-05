package com.hanggrian.rulebook.checkstyle;

public class DefaultDenesting {
    public void foo(int bar) {
        switch (bar) {
            case 0:
                return;
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
