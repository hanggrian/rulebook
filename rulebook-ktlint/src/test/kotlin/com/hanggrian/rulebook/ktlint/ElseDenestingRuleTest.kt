package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.ElseDenestingRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class ElseDenestingRuleTest {
    private val assertThatCode = assertThatRule { ElseDenestingRule() }

    @Test
    fun `Rule properties`() = ElseDenestingRule().assertProperties()

    @Test
    fun `No throw or return in if`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    baz()
                } else if (false) {
                    baz()
                } else {
                    baz()
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Lift else when if has return`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    throw Exception()
                } else if (false) {
                    return
                } else {
                    baz()
                }
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(6, 7, Messages[MSG])

    @Test
    fun `Skip if not all if blocks have return or throw`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    return
                } else if (false) {
                    baz()
                } else {
                    baz()
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Consider if-else without blocks`() =
        assertThatCode(
            """
            fun foo() {
                if (true) throw Exception()
                else if (false) return
                else baz()
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(4, 5, Messages[MSG])
}
