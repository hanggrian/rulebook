package com.hanggrian.rulebook.checkstyle.checks;

class UnnecessaryAbstract {
    abstract class Foo extends Baz {
        void baz() {}
    }

    class Baz {}
}
