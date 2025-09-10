package com.hanggrian.rulebook.checkstyle.checks;

import java.util.Arrays;

class UnnecessaryParenthesesInLambda {
    UnnecessaryParenthesesInLambda() {
        Arrays
            .asList(1, 2)
            .forEach((i) -> {});
    }
}
