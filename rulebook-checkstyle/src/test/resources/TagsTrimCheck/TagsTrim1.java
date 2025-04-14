package com.hanggrian.rulebook.checkstyle;

import java.util.ArrayList;

class TagsTrim {
    <
        T
    > void foo() {
        List<
            T
        > list = new ArrayList<>();
    }
}
