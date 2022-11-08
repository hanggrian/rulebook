package com.hendraanggrian.codestyle.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class JavadocTagDescriptionCheckTest {

    @Test
    fun test() {
        val checker = prepareCheckStyleChecker<JavadocTagDescriptionCheck>()
        val files = prepareFilesToBeChecked<JavadocTagDescriptionCheckTest>()
        val numberOfErrors = checker.process(files)
        assertEquals(3, numberOfErrors)
    }
}
