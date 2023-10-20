package com.hendraanggrian.rulebook

import com.hendraanggrian.rulebook.codenarc.RenameUncommonGenericsNarc
import com.hendraanggrian.rulebook.codenarc.RenameUncommonGenericsVisitor.Companion.MSG
import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertEquals

class RenameUncommonGenericsNarcTest : AbstractRuleTestCase<RenameUncommonGenericsNarc>() {
    override fun createRule(): RenameUncommonGenericsNarc = RenameUncommonGenericsNarc()

    @Test
    fun `Rule properties`() {
        assertEquals(3, rule.priority)
        assertEquals("RenameUncommonGenerics", rule.name)
    }

    @Test
    fun `Common generic type in class`() =
        assertNoViolations(
            """
            class MyClass<T> {}
            interface MyInterface<T> {}
            """.trimIndent(),
        )

    @Test
    fun `Uncommon generic type in class`() =
        assertTwoViolations(
            """
            class MyClass<X> {}
            interface MyInterface<X> {}
            """.trimIndent(),
            1,
            "class MyClass<X> {}",
            Messages[MSG],
            2,
            "interface MyInterface<X> {}",
            Messages[MSG],
        )

    @Test
    fun `Common generic type in function`() =
        assertNoViolations(
            """
            <E> void execute(List<E> list) {}
            """.trimIndent(),
        )

    @Test
    fun `Uncommon generic type in function`() =
        assertSingleViolation(
            """
            <X> void execute(List<X> list) {}
            """.trimIndent(),
            1,
        )
}
