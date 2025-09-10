package com.hanggrian.rulebook.checkstyle.checks;

class IllegalClassFinalName {
    class SpaceshipManager {}

    interface RocketManager {}

    @interface NavigatorManager {}

    enum PlanetManager {}
}
