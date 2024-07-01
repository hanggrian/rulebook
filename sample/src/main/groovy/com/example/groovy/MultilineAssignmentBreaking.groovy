package com.example.groovy

import groovy.transform.CompileStatic

@CompileStatic
class MultilineAssignmentBreaking {
    void foo() {
        int bar =
            1 +
                2
    }
}
