package com.example.groovy

class NoParenthesesInLambda {
    void foo() {
        Arrays
            .asList(1, 2, 3)
            .forEach(a -> {})
    }
}
