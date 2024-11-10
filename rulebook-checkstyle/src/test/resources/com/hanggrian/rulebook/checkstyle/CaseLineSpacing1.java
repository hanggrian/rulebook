package com.hanggrian.rulebook.checkstyle;

public class CaseLineSpacing {
    public foo(int bar) {
        switch (bar) {
            case 0:
                baz();
            case 1:
                baz();
                break;

            default:
                baz();
        }
    }

    public void baz() {}
}
