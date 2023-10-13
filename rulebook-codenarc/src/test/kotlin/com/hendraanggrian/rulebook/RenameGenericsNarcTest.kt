package com.hendraanggrian.rulebook

import com.hendraanggrian.rulebook.codenarc.Messages
import com.hendraanggrian.rulebook.codenarc.RenameGenericsNarc
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
    fun `Uncommon generic type in class`() = assertViolations(
        """
        class MyClass<X> {}
        interface MyInterface<X> {}
        """.trimIndent(),
        mutableMapOf(
            "line" to 1,
            "source" to "class MyClass<X> {}",
            "message" to Messages[RenameGenericsNarc.MSG]
        ),
        mutableMapOf(
            "line" to 2,
            "source" to "interface MyInterface<X> {}",
            "message" to Messages[RenameGenericsNarc.MSG]
        )
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
