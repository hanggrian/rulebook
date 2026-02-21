package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test

class ImportOrderRuleTest : RuleTest<ImportOrderRule>() {
    override fun createRule() = ImportOrderRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

    @Test
    fun `Correct import layout`() =
        assertNoViolations(
            """
            import com.example.User
            import java.util.List

            class Foo {
                def bar(List<User> list) {}
            }
            """.trimIndent(),
        )

    @Test
    fun `Incorrect import sorting`() =
        assertSingleViolation(
            """
            import java.util.List
            import com.example.User

            class Foo {
                def bar(List<User> list) {}
            }
            """.trimIndent(),
            2,
            "import com.example.User",
            "Arrange directive 'com.example.User' before 'java.util.List'.",
        )

    @Test
    fun `Separated import statements`() =
        assertSingleViolation(
            """
            import com.example.User

            import java.util.List

            class Foo {
                def bar(List<User> list) {}
            }
            """.trimIndent(),
            3,
            "import java.util.List",
            "Remove blank line before directive 'java.util.List'.",
        )

    @Test
    fun `Incorrect static import sorting`() =
        assertSingleViolation(
            """
            import com.example.User
            import java.util.List

            import static java.lang.System.exit
            import static java.lang.System.arraycopy

            class Foo {
                def bar(List<User> list) {
                    arraycopy(new int[]{0})
                    exit(0)
                }
            }
            """.trimIndent(),
            5,
            "import static java.lang.System.arraycopy",
            "Arrange directive 'java.lang.System.arraycopy' before 'java.lang.System.exit'.",
        )
}
