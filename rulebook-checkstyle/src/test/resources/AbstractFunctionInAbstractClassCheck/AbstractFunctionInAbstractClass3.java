package com.hanggrian.rulebook.checkstyle.checks;

class AbstractFunctionInAbstractClass {
    abstract class Foo extends Baz {
        void baz() {}
    }

    class Baz {}
}
