package com.hanggrian.rulebook.checkstyle.checks;

class UnnecessaryReturn {
    void foo() {
        System.out.println(item);
        return;

        // Lorem ipsum.
    }
}
