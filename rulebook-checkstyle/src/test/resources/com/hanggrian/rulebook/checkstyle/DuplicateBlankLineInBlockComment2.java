package com.hanggrian.rulebook.checkstyle;

class DuplicateBlankLineInBlockComment {
    /**
     * Lorem ipsum
     *
     *
     * dolor sit amet.
     */
    void foo() {}
}
