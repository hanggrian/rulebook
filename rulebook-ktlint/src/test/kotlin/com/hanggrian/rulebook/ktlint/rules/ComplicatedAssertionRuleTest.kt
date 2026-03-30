package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ComplicatedAssertionRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { ComplicatedAssertionRule() }

    @Test
    fun `Rule properties`() = ComplicatedAssertionRule().assertProperties()

    @Test
    fun `Targeted assertion calls`() =
        assertThatCode(
            """
            fun foo() {
                assertEquals(1, 2)
                assertNotEquals(1, 2)
                assertTrue(false)
                assertNull(s)
            }
            """.trimIndent(),
        ).asTest()
            .hasNoLintViolations()

    @Test
    fun `Generic assertion calls`() =
        assertThatCode(
            """
            fun foo() {
                assertTrue(1 == 2)
                assertFalse(1 != 2)
                assertEquals(true, 1 == 2)
                assertEquals(null, 1)
            }
            """.trimIndent(),
        ).asTest()
            .hasLintViolationsWithoutAutoCorrect(
                LintViolation(2, 5, "Use assertion 'assertEquals'."),
                LintViolation(3, 5, "Use assertion 'assertNotEquals'."),
                LintViolation(4, 5, "Use assertion 'assertTrue'."),
                LintViolation(5, 5, "Use assertion 'assertNull'."),
            )
}
