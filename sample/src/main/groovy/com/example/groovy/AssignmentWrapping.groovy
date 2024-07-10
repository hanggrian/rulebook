package com.example.groovy

import groovy.transform.CompileStatic

@CompileStatic
class AssignmentWrapping {
    void foo() {
        int bar =
            1 +
                2
    }
}
