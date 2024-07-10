package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ExceptionExtendingCheckTest {
    private val checker = checkerOf<ExceptionExtendingCheck>()

    @Test
    fun `Rule properties`(): Unit = ExceptionExtendingCheck().assertProperties()

    @Test
    fun `Extend user exceptions`() = assertEquals(0, checker.read("ExceptionExtending1"))

    @Test
    fun `Extend non-user exceptions`() = assertEquals(2, checker.read("ExceptionExtending2"))
}
