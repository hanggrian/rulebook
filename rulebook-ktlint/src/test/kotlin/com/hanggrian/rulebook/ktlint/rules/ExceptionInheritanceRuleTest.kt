package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ExceptionInheritanceRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { ExceptionInheritanceRule() }

    @Test
    fun `Rule properties`() = ExceptionInheritanceRule().assertProperties()

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

            class Baz : Throwable {
                constructor()

                constructor(message: String): super(message)
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 13, "Extend from class 'Exception'."),
            LintViolation(3, 13, "Extend from class 'Exception'."),
            LintViolation(5, 13, "Extend from class 'Exception'."),
        )
}
