package com.hanggrian.rulebook.checkstyle.checks;

class NestedIfElse {
    void foo() {
        if (true) {
            if (true) {
                baz();
                baz();
            }
        }
        baz();
    }

    void bar() {
        if (true) {
            try {
                if (true) {
                    baz();
                    baz();
                }
            } catch (Exception e) {
                try {
                    if (true) {
                        baz();
                        baz();
                    }
                } catch (Exception e) {
                    if (true) {
                        baz();
                        baz();
                    }
                }
            }
        }
        baz();
    }

    void baz() {}
}
