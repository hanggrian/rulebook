package com.example.groovy

class EmptyTagsClip {
    def foo() {
        Bar<Integer> bar = new Bar<>()
    }

    class Bar<T> {}
}
