package com.hanggrian.rulebook.checkstyle.checks;

class RedundantIf {
    boolean foo() {
        if (true) {
            return true;
        } else {
            return false;
        }

        // Lorem ipsum.
    }

    void baz() {}
}
