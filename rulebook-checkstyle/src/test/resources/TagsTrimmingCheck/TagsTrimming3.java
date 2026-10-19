package com.hanggrian.rulebook.checkstyle.checks;

import java.util.ArrayList;

class TagsTrimming {
    <
        // Lorem
        T
        // ipsum.
    > void foo() {
        List<
            // Lorem
            T
            // ipsum.
        > list = new ArrayList<>();
    }
}
