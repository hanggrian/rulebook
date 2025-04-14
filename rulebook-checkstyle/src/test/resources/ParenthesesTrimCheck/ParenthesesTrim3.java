package com.hanggrian.rulebook.checkstyle;

class ParenthesesTrim {
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
