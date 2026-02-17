package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.assertProperties
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class UnnecessaryAbstractRuleTest {
    private val assertThatCode = assertThatRule { UnnecessaryAbstractRule() }

    @Test
    fun `Rule properties`() = UnnecessaryAbstractRule().assertProperties()

    @Test
    fun `Abstract class with abstract function`() =
        assertThatCode(
            """
            abstract class Foo {
                abstract fun baz()
            }

            abstract class Bar {
                abstract var baz
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Abstract class without abstract function`() =
        assertThatCode(
            """
            abstract class Foo {
                fun baz()
            }

            abstract class Bar {
                var baz
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 1, "Omit 'abstract' modifier."),
            LintViolation(5, 1, "Omit 'abstract' modifier."),
        )

    @Test
    fun `Skip class with inheritance`() =
        assertThatCode(
            """
            abstract class Foo : Bar {
                fun baz()
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
