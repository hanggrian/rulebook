package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.assertProperties
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class RootProjectNameRuleTest : AbstractRuleTestCase<RootProjectNameRule>() {
    override fun createRule() = RootProjectNameRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<RootProjectNameVisitor>(rule.astVisitor)
    }

    @Test
    fun `Correct project name`() {
        sourceCodeName = "settings.gradle"
        assertNoViolations(
            """
            rootProject.name = 'my-project'
            """.trimIndent(),
        )
    }

    @Test
    fun `Incorrect project name`() {
        sourceCodeName = "settings.gradle"
        assertSingleViolation(
            """
            rootProject.name = 'my project'
            """.trimIndent(),
            1,
            "rootProject.name = 'my project'",
            "Root project name cannot contain space or special character.",
        )
    }

    @Test
    fun `Missing project name`() {
        sourceCodeName = "settings.gradle"
        assertSingleViolation(
            """
            pluginManagement.repositories.mavenCentral()
            dependencyResolutionManagement.repositories.mavenCentral()
            """.trimIndent(),
            1,
            "pluginManagement.repositories.mavenCentral()",
            "Set the root project name.",
        )
    }
}
