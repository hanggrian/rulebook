package com.hanggrian.rulebook.codenarc.rules

import kotlin.test.Test
import kotlin.test.assertIs

class GenericNameRuleTest : RuleTest<GenericNameRule>() {
    override fun createRule() = GenericNameRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<GenericNameVisitor>(rule.astVisitor)
    }

    @Test
    fun `Correct generic name in class`() =
        assertNoViolations(
            """
            class MyClass<GenericValue> {}

            interface MyInterface<E> {}
            """.trimIndent(),
        )

    @Test
    fun `Incorrect generic name in class`() =
        assertTwoViolations(
            """
            class MyClass<GENERIC_VALUE> {}

            interface MyInterface<ELEMENT> {}
            """.trimIndent(),
            1,
            "class MyClass<GENERIC_VALUE> {}",
            "Use pascal-case name.",
            3,
            "interface MyInterface<ELEMENT> {}",
            "Use pascal-case name.",
        )

    @Test
    fun `Correct generic name in function`() =
        assertNoViolations(
            """
            <E> void execute(List<E> list) {}
            """.trimIndent(),
        )

    @Test
    fun `Incorrect generic name in function`() =
        assertSingleViolation(
            """
            <ELEMENT> void execute(List<ELEMENT> list) {}
            """.trimIndent(),
            1,
            "<ELEMENT> void execute(List<ELEMENT> list) {}",
            "Use pascal-case name.",
        )
}
