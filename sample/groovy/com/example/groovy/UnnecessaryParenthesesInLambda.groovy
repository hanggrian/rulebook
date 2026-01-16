package com.example.groovy

class UnnecessaryParenthesesInLambda {
    void foo() {
        Arrays
            .asList(1, 2, 3)
            .forEach(a -> {})
    }
}
