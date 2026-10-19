package com.hanggrian.rulebook.checkstyle.checks;

class NoRedundantElse {
    void foo() {
        if (true) throw new Exception();
        else if (false) return;
        else baz();
    }

    void baz() {}
}
