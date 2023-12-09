package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class PropertyIdiomaticNamingCheckTest {
    private val checker = prepareChecker(PropertyIdiomaticNamingCheck::class)

    @Test
    fun `Descriptive names`() =
        assertEquals(0, checker.process(prepareFiles("PropertyIdiomaticNaming1")))

    @Test
    fun `Class names`() = assertEquals(2, checker.process(prepareFiles("PropertyIdiomaticNaming2")))
}
