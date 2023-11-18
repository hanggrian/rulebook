package com.example.groovy

import groovy.transform.CompileStatic

@CompileStatic
class UseCommonGenerics {
    class Point<N> {
        N x
    }

    <E> void sum(List<E> list) {
    }
}
