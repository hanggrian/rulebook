package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.MemberOrderRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class MemberOrderRuleTest {
    private val assertThatCode = assertThatRule { MemberOrderRule() }

    @Test
    fun `Rule properties`() = MemberOrderRule().assertProperties()

    @Test
    fun `Correct member layout`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                val bar = 0

                init {}

                constructor() : this(0)

                fun baz() {}

                companion object {}

                class Baz {}
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Member property after initializer block`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                init {}

                val bar = 0
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(
            4,
            5,
            Messages.get(MSG, "property", "initializer"),
        )

    @Test
    fun `Member initializer block after constructor`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                constructor() : this(0)

                init {}
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(
            4,
            5,
            Messages.get(MSG, "initializer", "constructor"),
        )

    @Test
    fun `Member constructor after function`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                fun bar() {}

                constructor() : this(0)
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(4, 5, Messages.get(MSG, "constructor", "function"))

    @Test
    fun `Member function after companion object`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                companion object {}

                fun bar() {}
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(
            4,
            5,
            Messages.get(MSG, "function", "companion"),
        )

    @Test
    fun `Treat backing property as function`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                val bar
                    get() = 0

                constructor() : this(0)
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(5, 5, Messages.get(MSG, "constructor", "function"))
}
