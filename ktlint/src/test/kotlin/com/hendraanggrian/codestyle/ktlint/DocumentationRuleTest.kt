package com.hendraanggrian.codestyle.ktlint

import com.hendraanggrian.codestyle.ktlint.DocumentationRule.Companion.ERROR_MESSAGE_CONTENT
import com.hendraanggrian.codestyle.ktlint.DocumentationRule.Companion.ERROR_MESSAGE_TAG
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class DocumentationRuleTest {
    private val assertCode = assertThatRule { DocumentationRule() }

    @Test
    fun `First word of a line in content`() {
        assertCode(
            """
                /**
                 * What is Lorem Ipsum? Lorem Ipsum is simply dummy text of the printing and
                 * `typesetting` industry.
                 *
                 * What is Lorem Ipsum? Lorem Ipsum is simply dummy text of the printing and
                 * [typesetting](http://some.url/) industry.
                 */
                class MyClass
            """.trimIndent()
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 3, ERROR_MESSAGE_CONTENT.format("code")),
            LintViolation(6, 3, ERROR_MESSAGE_CONTENT.format("link"))
        )
    }

    @Test
    fun `Tags' descriptions are sentences`() {
        assertCode(
            """
                /**
                 * A group of *members*.
                 *
                 * This class has no useful logic; it's just a documentation example.
                 *
                 * @param T the type of a member in this group
                 * @property name the name of this group
                 * @constructor Creates an empty group
                 */
                class Group<T>(val name: String) {
                    /**
                     * Adds a [member] to this group.
                     * @receiver any objects
                     * @return the new size of the group
                     */
                    fun Any.add(member: T): Int { }
                }
            """.trimIndent()
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(6, 13, ERROR_MESSAGE_TAG.format("@param")),
            LintViolation(7, 19, ERROR_MESSAGE_TAG.format("@property")),
            LintViolation(8, 17, ERROR_MESSAGE_TAG.format("@constructor")),
            LintViolation(13, 18, ERROR_MESSAGE_TAG.format("@receiver")),
            LintViolation(14, 16, ERROR_MESSAGE_TAG.format("@return"))
        )
    }
}
