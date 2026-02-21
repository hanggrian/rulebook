package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
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
        assertTwoViolations(
            """
            plugins.withType(MyPlugin) {
                property = true
            }

            tasks {
                create('myTask')
            }
            """.trimIndent(),
            1,
            "plugins.withType(MyPlugin) {",
            "Replace eager call with lazy 'configureEach'.",
            6,
            "create('myTask')",
            "Replace eager call with lazy 'register'.",
        )
    }
}
