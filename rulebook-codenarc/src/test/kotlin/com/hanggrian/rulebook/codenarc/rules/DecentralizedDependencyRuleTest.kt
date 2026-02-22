package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test
import kotlin.test.assertIs

class DecentralizedDependencyRuleTest : RuleTest<DecentralizedDependencyRule>() {
    override fun createRule() = DecentralizedDependencyRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<DecentralizedDependencyVisitor>(rule.astVisitor)
    }

    @Test
    fun `Centralized dependency`() {
        asScript()
        assertNoViolations(
            """
            dependencies {
                implementation(libs.artifact)
            }

            dependencies.implementation(libs.artifact)
            """.trimIndent(),
        )
    }

    @Test
    fun `Decentralized dependency`() {
        asScript()
        assertTwoViolations(
            """
            dependencies {
                implementation('repository:artifact:1.0')
            }

            dependencies.implementation("repository:artifact:1.0")
            """.trimIndent(),
            2,
            "implementation('repository:artifact:1.0')",
            "Declare dependency in version catalog.",
            5,
            "dependencies.implementation(\"repository:artifact:1.0\")",
            "Declare dependency in version catalog.",
        )
    }

    @Test
    fun `Skip importing project`() {
        asScript()
        assertNoViolations(
            """
            dependencies {
                implementation(project(':project'))
            }

            dependencies.implementation(project(":project"))
            """.trimIndent(),
        )
    }
}
