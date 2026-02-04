package com.example.groovy

class TagsClip {
    def foo() {
        Bar<Integer> bar = new Bar<>()
    }

    class Bar<T> {}
}
