package com.hanggrian.rulebook.codenarc.internals

import org.codehaus.groovy.ast.AnnotatedNode

internal fun AnnotatedNode.hasAnnotation(name: String): Boolean =
    annotations.any { it.classNode.name == name }
