package com.hanggrian.rulebook.checkstyle;

import java.util.Arrays;

class UnnecessaryParenthesesInLambda {
    UnnecessaryParenthesesInLambda() {
        Arrays
            .asList(1, 2)
            .forEach((i) -> {});
    }
}
