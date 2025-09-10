package com.hanggrian.rulebook.checkstyle.checks;

class ExceptionInheritance {
    class Foo extends Throwable {}

    class Bar extends Error {}
}
