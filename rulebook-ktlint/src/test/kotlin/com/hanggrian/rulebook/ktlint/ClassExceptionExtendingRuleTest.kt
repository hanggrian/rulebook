package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.ClassExceptionExtendingRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ClassExceptionExtendingRuleTest {
    private val assertThatCode = assertThatRule { ClassExceptionExtendingRule() }

    @Test
    fun `Rule properties`() = ClassExceptionExtendingRule().assertProperties()

    @Test
    fun `Extend user exceptions`() =
        assertThatCode(
            """
            class Foo : Exception()
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Extend non-user exceptions`() =
        assertThatCode(
            """
            class Foo : Throwable()

            class Bar : Error()
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 13, Messages[MSG]),
            LintViolation(3, 13, Messages[MSG]),
        )

    @Test
    fun `Extend without constructor callee`() =
        assertThatCode(
            """
            class Foo : Throwable {
                constructor()

                constructor(message: String): super(message)
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(1, 13, Messages[MSG])
}
