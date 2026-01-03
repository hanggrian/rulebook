package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.assertProperties
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
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
            "Arrange member 'property' before 'initializer'.",
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
            "Arrange member 'initializer' before 'constructor'.",
        )

    @Test
    fun `Constructor after function`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                fun baz() {}

                constructor() : this(0)
            }

            class Bar(b: Int) {
                val baz
                    get() = 0

                constructor() : this(0)
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(4, 5, "Arrange member 'constructor' before 'function'."),
            LintViolation(11, 5, "Arrange member 'constructor' before 'function'."),
        )

    @Test
    fun `Function after companion object`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                companion object {}

                fun baz() {}
            }

            class Bar(b: Int) {
                companion object {}

                val baz
                    get() = 0
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(4, 5, "Arrange member 'function' before 'companion object'."),
            LintViolation(10, 5, "Arrange member 'function' before 'companion object'."),
        )
}
