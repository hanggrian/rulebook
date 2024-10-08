package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.ImportOrderingRule.Companion.MSG_JOIN
import com.hanggrian.rulebook.codenarc.ImportOrderingRule.Companion.MSG_SORT
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class ImportOrderingRuleTest : AbstractRuleTestCase<ImportOrderingRule>() {
    override fun createRule() = ImportOrderingRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
    }

    @Test
    fun `Correct import layout`() =
        assertNoViolations(
            """
            import com.example.User
            import java.util.List

            class Foo {
                void bar(List<User> list) {}
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
                void bar(List<User> list) {}
            }
            """.trimIndent(),
            2,
            "import com.example.User",
            Messages.get(MSG_SORT, "com.example.User", "java.util.List"),
        )

    @Test
    fun `Separated import statements`() =
        assertSingleViolation(
            """
            import com.example.User

            import java.util.List

            class Foo {
                void bar(List<User> list) {}
            }
            """.trimIndent(),
            3,
            "import java.util.List",
            Messages.get(MSG_JOIN, "java.util.List"),
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
                void bar(List<User> list) {
                    arraycopy(new int[]{0})
                    exit(0)
                }
            }
            """.trimIndent(),
            5,
            "import static java.lang.System.arraycopy",
            Messages.get(MSG_SORT, "java.lang.System.arraycopy", "java.lang.System.exit"),
        )
}
