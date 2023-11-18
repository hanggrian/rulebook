package com.example.groovy

import groovy.transform.CompileStatic

@CompileStatic
class InvertIfCondition {
    void main(int i, int j) {
        if (!(i < j)) {
            return
        }
        int k = i
        k += j
    }
}
