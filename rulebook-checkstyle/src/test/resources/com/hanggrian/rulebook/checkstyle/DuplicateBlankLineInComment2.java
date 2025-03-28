package com.hanggrian.rulebook.checkstyle;

class DuplicateBlankLineInComment {
    void foo() {
        // Lorem ipsum
        //
        //
        // dolor sit amet.
    }
}
