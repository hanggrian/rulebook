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
            plugins {
                id("some-plugin")
            }

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
            buildscript {
                dependencies("some:library:1")
            }

            plugins.withType<MyPlugin> {
                property = true
            }

            tasks {
                create("myTask")
            }
            """.trimIndent(),
        ).asScript()
            .hasLintViolationsWithoutAutoCorrect(
                LintViolation(2, 5, "Replace eager call with lazy 'plugins'."),
                LintViolation(5, 9, "Replace eager call with lazy 'configureEach'."),
                LintViolation(10, 5, "Replace eager call with lazy 'register'."),
            )
}
