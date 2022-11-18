package com.hendraanggrian.lints.ktlint.kdoc

import com.hendraanggrian.lints.ktlint.kdoc.TagGroupStartingEmptyLineRule.Companion.ERROR_MESSAGE
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class TagGroupStartingEmptyLineRuleTest {
    private val assertThatCode = assertThatRule { TagGroupStartingEmptyLineRule() }

    @Test
    fun `No summary`() {
        assertThatCode(
            """
                /**
                 * @param width
                 * @param height
                 */
                fun createRectangle(width: Int, height: Int) { }
            """.trimIndent()
        ).hasNoLintViolations()
    }

    @Test
    fun `Correct formats`() {
        assertThatCode(
            """
                /**
                 * Just a box.
                 *
                 * @param width
                 * @param height
                 */
                fun createRectangle(width: Int, height: Int) { }

                /**
                 *
                 * @param width
                 * @param height
                 */
                fun createRectangle(width: Int, height: Int) { }
            """.trimIndent()
        ).hasNoLintViolations()
    }

    @Test
    fun `Missing empty line from summary`() {
        assertThatCode(
            """
                /**
                 * Just a box.
                 * @param width
                 * @param height
                 */
                fun summary(width: Int, height: Int) { }

                /**
                 * [Box].
                 * @param width
                 * @param height
                 */
                fun justLink(width: Int, height: Int) { }

                /**
                 * `Box`.
                 * @param width
                 * @param height
                 */
                fun justCode(width: Int, height: Int) { }
            """.trimIndent()
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 4, ERROR_MESSAGE.format("@param")),
            LintViolation(10, 4, ERROR_MESSAGE.format("@param")),
            LintViolation(17, 4, ERROR_MESSAGE.format("@param"))
        )
    }
}
