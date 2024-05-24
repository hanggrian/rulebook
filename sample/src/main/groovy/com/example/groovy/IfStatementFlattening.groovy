package com.example.groovy

class IfStatementFlattening<E> {
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
