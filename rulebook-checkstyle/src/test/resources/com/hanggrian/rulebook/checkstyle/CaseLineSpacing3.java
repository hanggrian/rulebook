package com.hanggrian.rulebook.checkstyle;

public class CaseLineSpacing {
    public foo(int bar) {
        switch (bar) {
            case 0:
                baz();
                break;
            default:
                baz();
                break;
        }
    }

    public void baz() {}
}
