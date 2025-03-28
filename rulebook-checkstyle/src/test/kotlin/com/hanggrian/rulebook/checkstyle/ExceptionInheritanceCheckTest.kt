package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ExceptionInheritanceCheckTest {
    private val checker = treeWalkerCheckerOf<ExceptionInheritanceCheck>()

    @Test
    fun `Rule properties`() = ExceptionInheritanceCheck().assertProperties()

    @Test
    fun `Extend user exceptions`() = assertEquals(0, checker.read("ExceptionInheritance1"))

    @Test
    fun `Extend non-user exceptions`() = assertEquals(2, checker.read("ExceptionInheritance2"))
}
