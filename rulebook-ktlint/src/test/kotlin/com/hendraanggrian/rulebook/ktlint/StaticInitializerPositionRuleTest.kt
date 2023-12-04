package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.StaticInitializerPositionRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class StaticInitializerPositionRuleTest {
    private val assertThatCode = assertThatRule { StaticInitializerPositionRule() }

    @Test
    fun `Correct format`() =
        assertThatCode(
            """
            class MyClass(num: Number) {
                val num2 = DEFAULT_VALUE

                constructor() : this(DEFAULT_VALUE)

                fun log() = print(DEFAULT_VALUE)

                companion object {
                    const val DEFAULT_VALUE = 0
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Static initializer before property`() =
        assertThatCode(
            """
            class MyClass(num: Number) {
                companion object {
                    const val DEFAULT_VALUE = 0
                }

                val num2 = DEFAULT_VALUE
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 5, Messages[MSG])

    @Test
    fun `Static initializer before constructor`() =
        assertThatCode(
            """
            class MyClass(num: Number) {
                companion object {
                    const val DEFAULT_VALUE = 0
                }

                constructor() : this(DEFAULT_VALUE)
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 5, Messages[MSG])

    @Test
    fun `Static initializer before method`() =
        assertThatCode(
            """
            class MyClass(num: Number) {
                companion object {
                    const val DEFAULT_VALUE = 0
                }

                fun log() = print(DEFAULT_VALUE)
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 5, Messages[MSG])
}
