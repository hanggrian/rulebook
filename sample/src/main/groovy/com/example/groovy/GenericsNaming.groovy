package com.example.groovy

import groovy.transform.CompileStatic

@CompileStatic
class GenericsNaming {
    class Foo<E> {}

    <N> void bar() {}
}
