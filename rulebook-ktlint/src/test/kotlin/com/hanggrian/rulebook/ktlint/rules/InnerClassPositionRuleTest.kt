package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class InnerClassPositionRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { InnerClassPositionRule() }

    @Test
    fun `Rule properties`() = InnerClassPositionRule().assertProperties()

    @Test
    fun `Inner classes at the bottom`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                val bar = 0

                constructor() : this(0)

                fun baz() = print(0)

                class Inner

                class AnotherInner
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Inner classes before members`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                class Inner

                val bar = 0

                class AnotherInner

                fun baz() = print(0)
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 5, "Move inner class to the bottom."),
            LintViolation(6, 5, "Move inner class to the bottom."),
        )

    @Test
    fun `Skip enum members with initialization`() =
        assertThatCode(
            """
            enum class Foo {
                BAR {
                    override fun baz() {}
                };

                abstract fun bar()
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Target all class-like declarations`() =
        assertThatCode(
            """
            interface OuterInterface {
                interface InnerInterface

                val member = 0
            }

            annotation class OuterAnnotationClass {
                annotation class InnerAnnotationClass

                val member = 0
            }

            enum class OuterEnumClass {
                FOO;

                enum class InnerEnumClass

                val member = 0
            }

            sealed class OuterSealedClass {
                annotation class InnerSealedClass

                val member = 0
            }

            data class OuterDataClass {
                annotation class InnerDataClass

                val member = 0
            }

            object OuterObject {
                object InnerObject

                val member = 0
            }

            data object OuterDataObject {
                data object InnerObject

                val member = 0
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 5, "Move inner class to the bottom."),
            LintViolation(8, 5, "Move inner class to the bottom."),
            LintViolation(16, 5, "Move inner class to the bottom."),
            LintViolation(22, 5, "Move inner class to the bottom."),
            LintViolation(28, 5, "Move inner class to the bottom."),
            LintViolation(34, 5, "Move inner class to the bottom."),
            LintViolation(40, 5, "Move inner class to the bottom."),
        )
}
