package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ExceptionThrowingCheckTest {
    private val checker = prepareChecker(ExceptionThrowingCheck::class)

    @Test
    fun `Throw subclass exceptions`() =
        assertEquals(0, checker.process(prepareFiles("ExceptionThrowing1")))

    @Test
    fun `Throw superclass exceptions`() =
        assertEquals(3, checker.process(prepareFiles("ExceptionThrowing2")))

    @Test
    fun `Throwing exceptions by reference`() =
        assertEquals(0, checker.process(prepareFiles("ExceptionThrowing3")))
}
