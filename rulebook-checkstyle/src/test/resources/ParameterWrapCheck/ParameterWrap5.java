package com.hanggrian.rulebook.checkstyle.checks;

class ParameterWrap {
    void foo(
        int a,
        // Comment
        int b,
        /** Block comment */
        int c,
        /**
         * Long block comment
         */
        int d
    ) {}
}
