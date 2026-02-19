package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.assertProperties
import com.hanggrian.rulebook.codenarc.violationOf
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class UnnecessaryScopeRuleTest : AbstractRuleTestCase<UnnecessaryScopeRule>() {
    override fun createRule() = UnnecessaryScopeRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<UnnecessaryScopeVisitor>(rule.astVisitor)
    }

    @Test
    fun `Single named-domain calls without scopes`() {
        sourceCodeName = "build.gradle"
        assertNoViolations(
            """
            repositories mavenCentral()

            dependencies implementation('some:library:1')

            configurations resolvable('compileClasspath') {
                extendsFrom(implementation)
            }

            tasks register('clean') {}

            groovy {
                sourceSets.main.srcDir('groovy')
            }
            """.trimIndent(),
        )
    }

    @Test
    fun `Single named-domain calls with scopes`() {
        sourceCodeName = "build.gradle"
        assertViolations(
            """
            repositories { mavenCentral() }

            dependencies { implementation('some:library:1') }

            configurations {
                resolvable('compileClasspath') {
                    extendsFrom(implementation)
                }
            }

            tasks {
                register('clean') {}
            }

            groovy {
                sourceSets {
                    main.srcDir('groovy')
                }
            }
            """.trimIndent(),
            violationOf(1, "repositories { mavenCentral() }", "Call statement directly."),
            violationOf(
                3,
                "dependencies { implementation('some:library:1') }",
                "Call statement directly.",
            ),
            violationOf(6, "resolvable('compileClasspath') {", "Call statement directly."),
            violationOf(12, "register('clean') {}", "Call statement directly."),
            violationOf(17, "main.srcDir('groovy')", "Call statement directly."),
        )
    }

    @Test
    fun `Multiple named-domain calls`() {
        sourceCodeName = "build.gradle"
        assertNoViolations(
            """
            repositories {
                mavenCentral()
                mavenCentral()
            }

            dependencies {
                implementation('some:library:1')
                implementation('some:library:1')
            }

            configurations {
                resolvable('compileClasspath') {
                    extendsFrom(implementation)
                }
                resolvable('compileClasspath') {
                    extendsFrom(implementation)
                }
            }

            tasks {
                register('clean') {}
                register('clean') {}
            }

            groovy {
                sourceSets.main {
                    srcDir('groovy')
                    srcDir('groovy')
                }
            }
            """.trimIndent(),
        )
    }
}
