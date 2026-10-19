package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class NoConfusingAssertionCheckTest : CheckTest() {
    override val check = NoConfusingAssertionCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Positive assertion calls`() = assertAll("NoConfusingAssertion1")

    @Test
    fun `Negative assertion calls`() =
        assertAll(
            "NoConfusingAssertion2",
            "7:19: Omit negation and replace assertion with 'assertFalse'.",
        )

    @Test
    fun `Skip chained conditions`() = assertAll("NoConfusingAssertion3")
}
