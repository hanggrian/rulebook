package com.hanggrian.rulebook.checkstyle.checks;

class NoDuplicateBlankLineInComment {
    void foo() {
        // Lorem ipsum
        //
        //
        // dolor sit amet.
    }
}
