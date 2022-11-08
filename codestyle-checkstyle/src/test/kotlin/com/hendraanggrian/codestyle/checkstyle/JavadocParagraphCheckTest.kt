package com.hendraanggrian.codestyle.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class JavadocParagraphCheckTest {

    @Test
    fun test() {
        val checker = prepareCheckStyleChecker<JavadocParagraphCheck>()
        val files = prepareFilesToBeChecked<JavadocParagraphCheckTest>()
        val numberOfErrors = checker.process(files)
        assertEquals(2, numberOfErrors)
    }
}
