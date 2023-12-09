package com.example.groovy

class IfStatementNesting<E> {
    List<E> elements

    void iterate() {
        if (elements == null) {
            return
        }
        for (element in elements) {
            // ...
        }
    }
}
