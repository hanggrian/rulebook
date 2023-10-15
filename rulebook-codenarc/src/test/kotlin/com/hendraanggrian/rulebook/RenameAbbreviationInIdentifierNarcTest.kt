package com.hendraanggrian.rulebook

import com.hendraanggrian.rulebook.codenarc.Messages
import com.hendraanggrian.rulebook.codenarc.RenameAbbreviationInIdentifierNarc
import com.hendraanggrian.rulebook.codenarc.RenameAbbreviationInIdentifierNarc.Companion.MSG
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertEquals

class RenameAbbreviationInIdentifierNarcTest :
    AbstractRuleTestCase<RenameAbbreviationInIdentifierNarc>() {

    override fun createRule(): RenameAbbreviationInIdentifierNarc =
        RenameAbbreviationInIdentifierNarc()

    @Test
    fun `Rule properties`() {
        assertEquals(3, rule.priority)
        assertEquals("RenameAbbreviationInIdentifier", rule.name)
    }

    @Test
    fun `Class names with lowercase abbreviation`() = assertNoViolations(
        """
        class MySqlClass {}
        interface MySqlInterface {}
        """.trimIndent()
    )

    // TODO: not yet supported
    // @Test
    fun `Class names with upppercase abbreviation`(): Unit = assertViolations(
        """
        class MySQLClass {}
        interface MySQLInterface {}
        """.trimIndent(),
        mapOf(
            "line" to 1,
            "source" to "class MySQLClass {}",
            "message" to Messages.get(MSG, "MySQLClass")
        ),
        mapOf(
            "line" to 2,
            "source" to "interface MySqlClass {}",
            "message" to Messages.get(MSG, "MySqlInterface")
        )
    )

    @Test
    fun `Property names with lowercase abbreviation`() = assertNoViolations(
        """
        class MyClass {
            var globalProperty = 1

            void foo() {
                var localProperty = 2 // not yet supported
            }
        }
        """.trimIndent()
    )

    @Test
    fun `Property names with uppercase abbreviation`() = assertSingleViolation(
        """
        class MyClass {
            var globalPROPERTY = 1

            void foo() {
                var localPROPERTY = 2 // not yet supported
            }
        }
        """.trimIndent(),
        2,
        "var globalPROPERTY = 1",
        Messages.get(MSG, "globalProperty")
    )

    @Test
    fun `Function names with lowercase abbreviation`() = assertNoViolations(
        """
        void myFunction() {}
        """.trimIndent()
    )

    @Test
    fun `Function names with uppercase abbreviation`() = assertSingleViolation(
        """
        void myFUNCTION() {}
        """.trimIndent(),
        1,
        "void myFUNCTION() {}",
        Messages.get(MSG, "myFunction")
    )

    @Test
    fun `Parameter names with lowercase abbreviation`() = assertNoViolations(
        """
        class Foo {
            Foo(int myParameter) {}
        }
        void bar(int myParameter) {}
        """.trimIndent()
    )

    @Test
    fun `Parameter names with uppercase abbreviation`() = assertTwoViolations(
        """
        class Foo {
            Foo(int myPARAMETER) {}
        }
        void bar(int myPARAMETER) {}
        """.trimIndent(),
        2,
        "Foo(int myPARAMETER) {}",
        Messages.get(MSG, "myParameter"),
        4,
        "void bar(int myPARAMETER) {}",
        Messages.get(MSG, "myParameter")
    )
}
