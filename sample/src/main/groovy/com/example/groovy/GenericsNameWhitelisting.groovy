package com.example.groovy

import groovy.transform.CompileStatic

@CompileStatic
class GenericsNameWhitelisting {
    <N> void bar() {}

    class Foo<E> {}
}
