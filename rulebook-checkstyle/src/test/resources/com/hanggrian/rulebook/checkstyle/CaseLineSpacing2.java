package com.hanggrian.rulebook.checkstyle;

public class CaseLineSpacing {
    public foo(int bar) {
        switch (bar) {
            case 0:
                baz();

            default:
                baz();
        }
    }

    public void baz() {}
}
