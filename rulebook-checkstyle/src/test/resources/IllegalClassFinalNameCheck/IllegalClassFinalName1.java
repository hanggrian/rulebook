package com.hanggrian.rulebook.checkstyle.checks;

class IllegalClassFinalName {
    class Spaceship {}

    interface Rocket {}

    @interface Navigator {}

    enum Planet {}
}
