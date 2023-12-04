package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.ConstructorPositionRule.Companion.MSG_METHODS
import com.hendraanggrian.rulebook.ktlint.ConstructorPositionRule.Companion.MSG_PROPERTIES
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class ConstructorPositionRuleTest {
    private val assertThatCode = assertThatRule { ConstructorPositionRule() }

    @Test
    fun `Correct format`() =
        assertThatCode(
            """
            class MyClass(num: Number) {
                init {
                    println(num)
                }

                constructor(num: Int) : this(num as Number)

                fun log() = println(num)
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Init after constructor`() =
        assertThatCode(
            """
            class MyClass(num: Number) {
                constructor(num: Int) : this(num as Number)

                init {
                    println(num)
                }
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 5, Messages[MSG_PROPERTIES])

    @Test
    fun `Property after constructor`() =
        assertThatCode(
            """
            class MyClass(num: Number) {
                constructor(num: Int) : this(num as Number)

                val num2 = num
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 5, Messages[MSG_PROPERTIES])

    @Test
    fun `Method before constructor`() =
        assertThatCode(
            """
            class MyClass(num: Number) {
                fun log() = println(num)

                constructor(num: Int) : this(num as Number)
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(4, 5, Messages[MSG_METHODS])
}
