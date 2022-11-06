package com.hendraanggrian.codestyle.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class DocumentationTagDescriptionCheckTest {

    @Test
    fun test() {
        val checker = prepareCheckStyleChecker<DocumentationTagDescriptionCheck>()
        val files = prepareFilesToBeChecked<DocumentationTagDescriptionCheckTest>()
        val numberOfErrors = checker.process(files)
        assertEquals(3, numberOfErrors)
    }
}
