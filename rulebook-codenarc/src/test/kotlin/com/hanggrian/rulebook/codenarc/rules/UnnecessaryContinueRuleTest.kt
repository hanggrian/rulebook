package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import com.hanggrian.rulebook.codenarc.violationOf
import kotlin.test.Test
import kotlin.test.assertIs

class UnnecessaryContinueRuleTest : RuleTest<UnnecessaryContinueRule>() {
    override fun createRule() = UnnecessaryContinueRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<UnnecessaryContinueVisitor>(rule.astVisitor)
    }

    @Test
    fun `Loops don't end with continue`() =
        assertNoViolations(
            """
            def foo(int... items) {
                for (item in items) {
                    println()
                }
            }

            def bar(int... items) {
                while (true) {
                    println()
                }
            }

            def baz(int... items) {
                do {
                    println()
                } while (true)
            }
            """.trimIndent(),
        )

    @Test
    fun `Loops end with continue`() =
        assertViolations(
            """
            def foo(int... items) {
                for (item in items) {
                    println()
                    continue
                }
            }

            def bar(int... items) {
                while (true) {
                    println()
                    continue
                }
            }

            def baz(int... items) {
                do {
                    println()
                    continue
                } while (true)
            }
            """.trimIndent(),
            violationOf(
                4,
                "continue",
                "Last 'continue' is not needed.",
            ),
            violationOf(
                11,
                "continue",
                "Last 'continue' is not needed.",
            ),
            violationOf(
                18,
                "continue",
                "Last 'continue' is not needed.",
            ),
        )

    @Test
    fun `Capture loops without block`() =
        assertViolations(
            """
            def foo(int... items) {
                for (item in items) continue
            }

            def bar(int... items) {
                while (true) continue
            }

            def baz(int... items) {
                do continue while (true)
            }
            """.trimIndent(),
            violationOf(
                2,
                "for (item in items) continue",
                "Last 'continue' is not needed.",
            ),
            violationOf(
                6,
                "while (true) continue",
                "Last 'continue' is not needed.",
            ),
            violationOf(
                10,
                "do continue while (true)",
                "Last 'continue' is not needed.",
            ),
        )

    @Test
    fun `Capture trailing non-continue`() =
        assertViolations(
            """
            def foo(int... items) {
                for (item in items) {
                    println()
                    continue

                    // Lorem ipsum.
                }
            }

            def bar(int... items) {
                while (true) {
                    println()
                    continue

                    // Lorem ipsum.
                }
            }

            def baz(int... items) {
                do {
                    println()
                    continue

                    // Lorem ipsum.
                } while (true)
            }
            """.trimIndent(),
            violationOf(
                4,
                "continue",
                "Last 'continue' is not needed.",
            ),
            violationOf(
                13,
                "continue",
                "Last 'continue' is not needed.",
            ),
            violationOf(
                22,
                "continue",
                "Last 'continue' is not needed.",
            ),
        )
}
