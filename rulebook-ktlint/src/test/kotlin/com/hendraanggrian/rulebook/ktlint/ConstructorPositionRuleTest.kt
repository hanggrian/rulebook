package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.ConstructorPositionRule.Companion.MSG_METHODS
import com.hendraanggrian.rulebook.ktlint.ConstructorPositionRule.Companion.MSG_PROPERTIES
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class ConstructorPositionRuleTest {
    private val assertThatCode = assertThatRule { ConstructorPositionRule() }

    @Test
    fun `Rule properties`(): Unit = ConstructorPositionRule().assertProperties()

    @Test
    fun `Properties, initializers, constructors, and methods`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                val bar = 0

                init {}

                constructor() : this(0)

                fun baz() {}
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Initializer after constructor`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                constructor() : this(0)

                init {}
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(4, 5, Messages[MSG_PROPERTIES])

    @Test
    fun `Property after constructor`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                constructor() : this(0)

                val bar = 0
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(4, 5, Messages[MSG_PROPERTIES])

    @Test
    fun `Method before constructor`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                fun baz() {}

                constructor() : this(0)
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 5, Messages[MSG_METHODS])
}
