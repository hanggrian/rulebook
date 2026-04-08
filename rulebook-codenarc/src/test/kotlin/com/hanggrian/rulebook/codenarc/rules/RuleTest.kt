package com.hanggrian.rulebook.codenarc.rules

import org.codenarc.rule.AbstractRule
import org.codenarc.rule.AbstractRuleTestCase
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

abstract class RuleTest<T : AbstractRule> : AbstractRuleTestCase<T>() {
    protected fun asScript() {
        sourceCodeName = "test.gradle"
    }

    protected fun asTest() {
        sourceCodePath = "test/"
        sourceCodeName = "SomeTest.groovy"
    }

    protected fun violationOf(line: Int, source: String, message: String) =
        mapOf(
            "line" to line,
            "source" to source,
            "message" to message,
        )

    protected inline fun <reified T : AbstractRule> T.assertProperties() {
        assertEquals(3, priority)
        assertEquals(T::class.java.simpleName.substringBefore("Rule"), name)
        assertThrows<UnsupportedOperationException> { name = "AnotherName" }
        assertThrows<UnsupportedOperationException> { priority = 1 }
    }
}
