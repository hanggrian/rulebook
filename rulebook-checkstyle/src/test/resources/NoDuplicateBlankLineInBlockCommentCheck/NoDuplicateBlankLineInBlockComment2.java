package com.hanggrian.rulebook.checkstyle.checks;

class NoDuplicateBlankLineInBlockComment {
    /**
     * Lorem ipsum
     *
     *
     * dolor sit amet.
     */
    void foo() {}
}
