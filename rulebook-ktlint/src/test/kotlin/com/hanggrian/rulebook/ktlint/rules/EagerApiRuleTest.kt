package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class EagerApiRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { EagerApiRule() }

    @Test
    fun `Rule properties`() = EagerApiRule().assertProperties()

    @Test
    fun `Lazy API`() =
        assertThatCode(
            """
            plugins.withType<MyPlugin>().configureEach {
                property = true
            }

            tasks {
                register("myTask")
            }
            """.trimIndent(),
        ).asScript()
            .hasNoLintViolations()

    @Test
    fun `Eager API`() =
        assertThatCode(
            """
            plugins.withType<MyPlugin> {
                property = true
            }

            tasks {
                create("myTask")
            }
            """.trimIndent(),
        ).asScript()
            .hasLintViolationsWithoutAutoCorrect(
                LintViolation(1, 9, "Replace eager call with lazy 'configureEach'."),
                LintViolation(6, 5, "Replace eager call with lazy 'register'."),
            )
}
