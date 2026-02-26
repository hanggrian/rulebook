package com.hanggrian.rulebook.checkstyle.checks;

class UnnecessaryContinue {
    void foo(int... items) {
        for (int item : items) {
            System.out.println(item);
            continue;

            // Lorem ipsum.
        }
    }

    void bar(int... items) {
        while (true) {
            System.out.println(item);
            continue;

            // Lorem ipsum.
        }
    }

    void baz(int... items) {
        do {
            System.out.println(item);
            continue;

            // Lorem ipsum.
        } while (true);
    }
}
