package com.example.groovy

import groovy.transform.CompileStatic

@CompileStatic
class GenericsCommonNaming {
    class Foo<E> {}

    <N> void bar() {}
}
