package com.example.groovy

import javax.lang.model.AnnotatedConstruct
import javax.lang.model.SourceVersion

import static java.lang.System.arraycopy
import static java.lang.System.exit

class ImportOrder {
    def foo(AnnotatedConstruct construct, SourceVersion version) {
        arraycopy(new int[]{0})
        exit(0)
    }
}
