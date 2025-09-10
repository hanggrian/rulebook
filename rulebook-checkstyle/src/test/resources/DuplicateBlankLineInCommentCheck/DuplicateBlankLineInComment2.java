package com.hanggrian.rulebook.checkstyle.checks;

class DuplicateBlankLineInComment {
    void foo() {
        // Lorem ipsum
        //
        //
        // dolor sit amet.
    }
}
