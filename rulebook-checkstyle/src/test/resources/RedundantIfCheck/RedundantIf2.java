package com.hanggrian.rulebook.checkstyle.checks;

class RedundantIf {
    boolean foo() {
        if (true) {
            return true;
        } else {
            return false;
        }
    }

    boolean bar() {
        if (true) {
            return true;
        }
        return false;
    }

    void baz() {}
}
