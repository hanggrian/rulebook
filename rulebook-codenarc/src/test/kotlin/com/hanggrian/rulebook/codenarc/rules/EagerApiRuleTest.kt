package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import com.hanggrian.rulebook.codenarc.violationOf
import kotlin.test.Test
import kotlin.test.assertIs

class EagerApiRuleTest : RuleTest<EagerApiRule>() {
    override fun createRule() = EagerApiRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<EagerApiVisitor>(rule.astVisitor)
    }

    @Test
    fun `Lazy API`() {
        asScript()
        assertNoViolations(
            """
            plugins {
                id('some-plugin')
            }

            plugins.withType(MyPlugin).configureEach {
                property = true
            }

            tasks {
                register('myTask')
            }
            """.trimIndent(),
        )
    }

    @Test
    fun `Eager API`() {
        asScript()
        assertViolations(
            """
            buildscript {
                dependencies('some:library:1')
            }

            plugins.withType(MyPlugin) {
                property = true
            }

            tasks {
                create('myTask')
            }
            """.trimIndent(),
            violationOf(
                1,
                "buildscript {",
                "Replace eager call with lazy 'plugins'.",
            ),
            violationOf(
                5,
                "plugins.withType(MyPlugin) {",
                "Replace eager call with lazy 'configureEach'.",
            ),
            violationOf(
                10,
                "create('myTask')",
                "Replace eager call with lazy 'register'.",
            ),
        )
    }
}
