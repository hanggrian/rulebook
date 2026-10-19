package com.hanggrian.rulebook.checkstyle.checks;

import java.util.Arrays;

class NoParenthesesInLambda {
    NoParenthesesInLambda() {
        Arrays
            .asList(1, 2)
            .forEach((i) -> {});
    }
}
