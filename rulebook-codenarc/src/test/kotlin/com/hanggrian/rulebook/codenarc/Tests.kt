package com.hanggrian.rulebook.codenarc

import org.codenarc.rule.AbstractRule
import org.codenarc.rule.AbstractRuleTestCase
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

fun violationOf(line: Int, source: String, message: String) =
    mapOf(
        "line" to line,
        "source" to source,
        "message" to message,
    )

abstract class RuleTest<T : AbstractRule> : AbstractRuleTestCase<T>() {
    fun asScript(name: String = "test.gradle") {
        sourceCodeName = name
    }

    inline fun <reified T : AbstractRule> T.assertProperties() {
        assertEquals(3, priority)
        assertEquals(T::class.java.simpleName.substringBefore("Rule"), name)
        assertThrows<UnsupportedOperationException> { name = "AnotherName" }
        assertThrows<UnsupportedOperationException> { priority = 1 }
    }
}
