package com.hanggrian.rulebook.checkstyle.checks;

class UnnecessaryContinue {
    void foo(int... items) {
        for (int item : items) continue;
    }

    void bar(int... items) {
        while (true) continue;
    }

    void baz(int... items) {
        do continue; while (true);
    }
}
