package com.example.groovy

class ConstructorPosition {
    private final String nickname

    ConstructorPosition(String name) {
        nickname = name.substring(0, 3)
    }

    void log() {
        System.out.println("Hi " + nickname + "!")
    }
}
