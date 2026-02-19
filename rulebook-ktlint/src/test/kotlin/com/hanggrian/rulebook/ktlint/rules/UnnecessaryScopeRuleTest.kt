package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.assertProperties
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class UnnecessaryScopeRuleTest {
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
        ).asFileWithPath("file.kts")
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
        ).asFileWithPath("file.kts")
            .hasLintViolationsWithoutAutoCorrect(
                LintViolation(1, 16, "Call statement directly."),
                LintViolation(3, 16, "Call statement directly."),
                LintViolation(6, 5, "Call statement directly."),
                LintViolation(12, 5, "Call statement directly."),
                LintViolation(17, 9, "Call statement directly."),
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
        ).asFileWithPath("file.kts")
            .hasNoLintViolations()

    @Test
    fun `Skip invocation with string as receiver`() =
        assertThatCode(
            """
            tasks {
                "clean" {}
            }
            """.trimIndent(),
        ).asFileWithPath("file.kts")
            .hasNoLintViolations()
}
