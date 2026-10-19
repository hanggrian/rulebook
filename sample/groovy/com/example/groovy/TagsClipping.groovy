package com.example.groovy

class TagsClipping {
    def foo() {
        Bar<Integer> bar = new Bar<>()
    }

    class Bar<T> {}
}
