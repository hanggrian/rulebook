package com.example.groovy

import groovy.transform.CompileStatic

@CompileStatic
class RenameUncommonGenerics {
    class Point<N> {
        N x
    }

    <E> void sum(List<E> list) {
    }
}
