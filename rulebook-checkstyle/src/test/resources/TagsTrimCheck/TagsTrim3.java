package com.hanggrian.rulebook.checkstyle;

import java.util.ArrayList;

class TagsTrim {
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
