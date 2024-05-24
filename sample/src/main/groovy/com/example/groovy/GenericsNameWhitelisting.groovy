package com.example.groovy

import groovy.transform.CompileStatic

@CompileStatic
class GenericsNameWhitelisting {
    class Foo<E> {}

    <N> void bar() {}
}
