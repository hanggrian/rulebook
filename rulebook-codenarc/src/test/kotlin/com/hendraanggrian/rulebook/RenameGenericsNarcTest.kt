package com.hendraanggrian.rulebook

import com.hendraanggrian.rulebook.codenarc.Messages
import com.hendraanggrian.rulebook.codenarc.RenameGenericsNarc
import com.hendraanggrian.rulebook.codenarc.RenameGenericsNarc.Companion.MSG
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertEquals

class RenameGenericsNarcTest : AbstractRuleTestCase<RenameGenericsNarc>() {
    override fun createRule(): RenameGenericsNarc = RenameGenericsNarc()

    @Test
    fun `Rule properties`() {
        assertEquals(3, rule.priority)
        assertEquals("RenameGenericType", rule.name)
    }

    @Test
    fun `Common generic type in class`() = assertNoViolations(
        """
        class MyClass<T> {}
        interface MyInterface<T> {}
        """.trimIndent()
    )

    @Test
    fun `Uncommon generic type in class`() = assertTwoViolations(
        """
        class MyClass<X> {}
        interface MyInterface<X> {}
        """.trimIndent(),
        1,
        "class MyClass<X> {}",
        Messages[MSG],
        2,
        "interface MyInterface<X> {}",
        Messages[MSG]
    )

    @Test
    fun `Common generic type in function`() = assertNoViolations(
        """
        <E> void execute(List<E> list) {}
        """.trimIndent()
    )

    @Test
    fun `Uncommon generic type in function`() = assertSingleViolation(
        """
        <X> void execute(List<X> list) {}
        """.trimIndent(),
        1
    )
}
