package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import com.hanggrian.rulebook.codenarc.violationOf
import kotlin.test.Test
import kotlin.test.assertIs

class UnnecessaryScopeRuleTest : RuleTest<UnnecessaryScopeRule>() {
    override fun createRule() = UnnecessaryScopeRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<UnnecessaryScopeVisitor>(rule.astVisitor)
    }

    @Test
    fun `Single named-domain calls without scopes`() {
        asScript()
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
        asScript()
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
            violationOf(1, "repositories { mavenCentral() }", "Replace braces with dot call."),
            violationOf(
                3,
                "dependencies { implementation('some:library:1') }",
                "Replace braces with dot call.",
            ),
            violationOf(6, "resolvable('compileClasspath') {", "Replace braces with dot call."),
            violationOf(12, "register('clean') {}", "Replace braces with dot call."),
            violationOf(17, "main.srcDir('groovy')", "Replace braces with dot call."),
        )
    }

    @Test
    fun `Multiple named-domain calls`() {
        sourceCodeName = "file.gradle"
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
