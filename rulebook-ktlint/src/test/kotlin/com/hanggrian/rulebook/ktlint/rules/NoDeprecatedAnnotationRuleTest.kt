package com.hanggrian.rulebook.ktlint.rules

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class NoDeprecatedAnnotationRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { NoDeprecatedAnnotationRule() }

    @Test
    fun `Rule properties`() = NoDeprecatedAnnotationRule().assertProperties()

    @Test
    fun `Kotlin test annotation in imports`() =
        assertThatCode("import kotlin.test.Test")
            .asTest()
            .hasNoLintViolations()

    @Test
    fun `JUnit test annotation in imports`() =
        assertThatCode("import org.junit.Test")
            .asTest()
            .hasLintViolationWithoutAutoCorrect(
                1,
                8,
                "Use independent annotation 'kotlin.test.Test'.",
            )

    @Test
    fun `Kotlin test annotation in type reference`() =
        assertThatCode(
            """
            @kotlin.test.Test
            fun testSomething() {
            }
            """.trimIndent(),
        ).asTest()
            .hasNoLintViolations()

    @Test
    fun `JUnit test annotation in type reference`() =
        assertThatCode(
            """
            @org.junit.Test
            fun testSomething() {
            }
            """.trimIndent(),
        ).asTest()
            .hasLintViolationWithoutAutoCorrect(
                1,
                2,
                "Use independent annotation 'kotlin.test.Test'.",
            )
}
