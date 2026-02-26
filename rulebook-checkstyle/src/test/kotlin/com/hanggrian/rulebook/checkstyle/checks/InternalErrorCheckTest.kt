package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class InternalErrorCheckTest : CheckTest() {
    override val check = InternalErrorCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Extend user exceptions`() = assertAll("InternalError1")

    @Test
    fun `Extend non-user exceptions`() =
        assertAll(
            "InternalError2",
            "4:23: Extend from class Exception.",
            "6:23: Extend from class Exception.",
        )
}
