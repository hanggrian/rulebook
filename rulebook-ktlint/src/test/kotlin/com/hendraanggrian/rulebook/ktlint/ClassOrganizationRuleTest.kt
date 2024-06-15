package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.ClassOrganizationRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class ClassOrganizationRuleTest {
    private val assertThatCode = assertThatRule { ClassOrganizationRule() }

    @Test
    fun `Rule properties`(): Unit = ClassOrganizationRule().assertProperties()

    @Test
    fun `Correct organization`() =
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
    fun `Property after initializer block`() =
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
            Messages.get(MSG, "property", "initializer block"),
        )

    @Test
    fun `Initializer block after constructor`() =
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
            Messages.get(MSG, "initializer block", "constructor"),
        )

    @Test
    fun `Constructor after function`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                fun bar() {}

                constructor() : this(0)
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(4, 5, Messages.get(MSG, "constructor", "function"))

    @Test
    fun `Function after companion object`() =
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
            Messages.get(MSG, "function", "companion object"),
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
