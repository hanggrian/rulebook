package com.hanggrian.rulebook.checkstyle;

public class IfFlattening {
    public IfFlattening() {
        if (true) {
            bar()
            baz()
        }
    }
}
