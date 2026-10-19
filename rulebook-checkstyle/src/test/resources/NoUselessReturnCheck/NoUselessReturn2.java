package com.hanggrian.rulebook.checkstyle.checks;

class NoUselessReturn {
    void foo() {
        System.out.println(item);
        return;
    }
}
