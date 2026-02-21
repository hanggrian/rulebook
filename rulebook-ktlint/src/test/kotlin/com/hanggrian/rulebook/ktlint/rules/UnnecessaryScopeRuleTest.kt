package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class UnnecessaryScopeRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { UnnecessaryScopeRule() }

    @Test
    fun `Rule properties`() = UnnecessaryScopeRule().assertProperties()

    @Test
    fun `Single named-domain calls without scopes`() =
        assertThatCode(
            """
            repositories.mavenCentral()

            dependencies.implementation("some:library:1")

            configurations.resolvable("compileClasspath") {
                extendsFrom(implementation)
            }

            tasks.register("clean") {}

            kotlin {
                sourceSets.main.srcDir("kotlin")
            }
            """.trimIndent(),
        ).asScript()
            .hasNoLintViolations()

    @Test
    fun `Single named-domain calls with scopes`() =
        assertThatCode(
            """
            repositories { mavenCentral() }

            dependencies { implementation("some:library:1") }

            configurations {
                resolvable("compileClasspath") {
                    extendsFrom(implementation)
                }
            }

            tasks {
                register("clean") {}
            }

            kotlin {
                sourceSets {
                    main.srcDir("kotlin")
                }
            }
            """.trimIndent(),
        ).asScript()
            .hasLintViolationsWithoutAutoCorrect(
                LintViolation(1, 16, "Replace braces with dot call."),
                LintViolation(3, 16, "Replace braces with dot call."),
                LintViolation(6, 5, "Replace braces with dot call."),
                LintViolation(12, 5, "Replace braces with dot call."),
                LintViolation(17, 9, "Replace braces with dot call."),
            )

    @Test
    fun `Multiple named-domain calls`() =
        assertThatCode(
            """
            repositories {
                mavenCentral()
                mavenCentral()
            }

            dependencies {
                implementation("some:library:1")
                implementation("some:library:1")
            }

            configurations {
                resolvable("compileClasspath") {
                    extendsFrom(implementation)
                }
                resolvable("compileClasspath") {
                    extendsFrom(implementation)
                }
            }

            tasks {
                register("clean") {}
                register("clean") {}
            }

            kotlin {
                sourceSets.main {
                    srcDir("kotlin")
                    srcDir("kotlin")
                }
            }
            """.trimIndent(),
        ).asScript()
            .hasNoLintViolations()

    @Test
    fun `Skip invocation with string as receiver`() =
        assertThatCode(
            """
            tasks {
                "clean" {}
            }
            """.trimIndent(),
        ).asScript()
            .hasNoLintViolations()
}
