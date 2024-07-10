package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.InnerClassPositionRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class InnerClassPositionRuleTest {
    private val assertThatCode = assertThatRule { InnerClassPositionRule() }

    @Test
    fun `Rule properties`(): Unit = InnerClassPositionRule().assertProperties()

    @Test
    fun `Inner class at the bottom`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                val bar = 0

                constructor() : this(0)

                fun baz() = print(0)

                class Inner
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Inner class before property`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                class Inner

                val bar = 0
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 5, Messages[MSG])

    @Test
    fun `Inner class before constructor`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                class Inner {}

                constructor() : this(0)
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 5, Messages[MSG])

    @Test
    fun `Inner class before method`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                class Inner

                fun baz() = print(0)
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 5, Messages[MSG])
}
