package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ClassExceptionExtendingCheckTest {
    private val checker = checkerOf<ClassExceptionExtendingCheck>()

    @Test
    fun `Rule properties`() = ClassExceptionExtendingCheck().assertProperties()

    @Test
    fun `Extend user exceptions`() = assertEquals(0, checker.read("ClassExceptionExtending1"))

    @Test
    fun `Extend non-user exceptions`() = assertEquals(2, checker.read("ClassExceptionExtending2"))
}
