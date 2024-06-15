package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ClassOrganizationCheckTest {
    private val checker = checkerOf<ClassOrganizationCheck>()

    @Test
    fun `Rule properties()`(): Unit = ClassOrganizationCheck().assertProperties()

    @Test
    fun `Correct organizations`() = assertEquals(0, checker.read("ClassOrganization1"))

    @Test
    fun `Property after constructor`() = assertEquals(1, checker.read("ClassOrganization2"))

    @Test
    fun `Constructor after function`() = assertEquals(1, checker.read("ClassOrganization3"))

    @Test
    fun `Skip static members`() = assertEquals(0, checker.read("ClassOrganization4"))
}
