package com.hanggrian.rulebook.checkstyle.checks;

class ParenthesesTrimming {
    void foo(
        // Lorem
        int bar
        // ipsum.
    ) {
        System.out.println(
            // Lorem
            "baz"
            // ipsum.
        );
    }
}
