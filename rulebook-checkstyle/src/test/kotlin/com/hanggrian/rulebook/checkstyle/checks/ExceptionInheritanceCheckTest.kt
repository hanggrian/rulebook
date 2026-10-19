package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class ExceptionInheritanceCheckTest : CheckTest() {
    override val check = ExceptionInheritanceCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Extend user exceptions`() = assertAll("ExceptionInheritance1")

    @Test
    fun `Extend non-user exceptions`() =
        assertAll(
            "ExceptionInheritance2",
            "4:23: Extend from class Exception.",
            "6:23: Extend from class Exception.",
        )
}
