package com.hanggrian.rulebook.checkstyle;

public class ControlFlowBracing {
    public void foo() {
        if (true)
            bar();
        else if (false) {
            baz();
        } else
            bar();
    }

    public void bar() {
    }

    public void baz() {
    }
}
