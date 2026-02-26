package com.hanggrian.rulebook.checkstyle.checks;

class InternalError {
    class Foo extends Throwable {}

    class Bar extends Error {}
}
