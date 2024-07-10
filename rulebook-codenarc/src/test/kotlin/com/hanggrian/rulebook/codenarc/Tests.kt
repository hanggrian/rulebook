package com.hanggrian.rulebook.codenarc

import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

internal inline fun <reified T : Rule> T.assertProperties() {
    assertEquals(3, priority)
    assertEquals(T::class.java.simpleName.substringBefore("Rule"), name)
    assertThrows<UnsupportedOperationException> { name = "AnotherName" }
    assertThrows<UnsupportedOperationException> { priority = 1 }
}

internal fun violationOf(line: Int, source: String, message: String) =
    mapOf(
        "line" to line,
        "source" to source,
        "message" to message,
    )
