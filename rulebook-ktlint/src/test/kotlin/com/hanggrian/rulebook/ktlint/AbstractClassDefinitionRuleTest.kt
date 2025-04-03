package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class AbstractClassDefinitionRuleTest {
    private val assertThatCode = assertThatRule { AbstractClassDefinitionRule() }

    @Test
    fun `Rule properties`() = AbstractClassDefinitionRule().assertProperties()

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
