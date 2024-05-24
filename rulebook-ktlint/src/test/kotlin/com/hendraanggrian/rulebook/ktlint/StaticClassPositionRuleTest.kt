package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.StaticClassPositionRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class StaticClassPositionRuleTest {
    private val assertThatCode = assertThatRule { StaticClassPositionRule() }

    @Test
    fun `Rule properties`(): Unit = StaticClassPositionRule().assertProperties()

    @Test
    fun `Static class at the bottom`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                val bar = VALUE

                constructor() : this(VALUE)

                fun baz() = print(VALUE)

                companion object {
                    const val VALUE = 0
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Static class before property`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                companion object {
                    const val VALUE = 0
                }

                val bar = VALUE
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 5, Messages[MSG])

    @Test
    fun `Static class before constructor`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                companion object {
                    const val VALUE = 0
                }

                constructor() : this(VALUE)
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 5, Messages[MSG])

    @Test
    fun `Static class before method`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                companion object {
                    const val VALUE = 0
                }

                fun baz() = print(VALUE)
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 5, Messages[MSG])
}
