package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class DecentralizedDependencyRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { DecentralizedDependencyRule() }

    @Test
    fun `Rule properties`() = DecentralizedDependencyRule().assertProperties()

    @Test
    fun `Centralized dependency`() =
        assertThatCode(
            """
            dependencies {
                implementation(libs.artifact)
            }

            dependencies.implementation(libs.artifact)
            """.trimIndent(),
        ).asScript()
            .hasNoLintViolations()

    @Test
    fun `Decentralized dependency`() =
        assertThatCode(
            """
            dependencies {
                implementation("repository:artifact:1.0")
            }

            dependencies.implementation("repository:artifact:1.0")
            """.trimIndent(),
        ).asScript()
            .hasLintViolationsWithoutAutoCorrect(
                LintViolation(2, 20, "Declare dependency in version catalog."),
                LintViolation(5, 29, "Declare dependency in version catalog."),
            )

    @Test
    fun `Skip importing project`() =
        assertThatCode(
            """
            dependencies {
                implementation(project(":project"))
            }

            dependencies.implementation(project(":project"))
            """.trimIndent(),
        ).asScript()
            .hasNoLintViolations()
}
