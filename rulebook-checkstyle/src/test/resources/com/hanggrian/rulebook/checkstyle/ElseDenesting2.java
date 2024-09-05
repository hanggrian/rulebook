package com.hanggrian.rulebook.checkstyle;

public class ElseDenesting {
    public void foo() {
        if (true) {
            throw new Exception();
        } else if (false) {
            return;
        } else {
            baz();
        }
    }

    public void baz() {}
}
