package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.assertProperties
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class TagsClipRuleTest : AbstractRuleTestCase<TagsClipRule>() {
    override fun createRule() = TagsClipRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

    @Test
    fun `Wrapped parentheses`() =
        assertNoViolations(
            """
            def foo() {
                List<Integer> nums =
                    new ArrayList<>()
            }
            """.trimIndent(),
        )

    @Test
    fun `Unwrapped parentheses`() =
        assertSingleViolation(
            """
            def foo() {
                List<Integer> nums =
                    new ArrayList< >()
            }
            """.trimIndent(),
            3,
            "new ArrayList< >()",
            "Convert into '<>'.",
        )

    @Test
    fun `Allow parentheses with comment`() =
        assertNoViolations(
            """
            def foo() {
                List<Integer> nums =
                    new ArrayList< /** Lorem ipsum. */ >()
            }
            """.trimIndent(),
        )
}
