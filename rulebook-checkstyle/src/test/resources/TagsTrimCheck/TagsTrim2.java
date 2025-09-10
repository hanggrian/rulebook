package com.hanggrian.rulebook.checkstyle.checks;

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
