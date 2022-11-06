package com.hendraanggrian.codestyle.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class DocumentationContentCheckTest {

    @Test
    fun test() {
        val checker = prepareCheckStyleChecker<DocumentationContentCheck>()
        val files = prepareFilesToBeChecked<DocumentationContentCheckTest>()
        val numberOfErrors = checker.process(files)
        assertEquals(2, numberOfErrors)
    }
}
