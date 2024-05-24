package com.hendraanggrian.rulebook.codenarc

import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

internal inline fun <reified T : RulebookRule> T.assertProperties() {
    assertEquals(3, priority)
    assertEquals(T::class.java.simpleName.substringBefore("Rule"), name)
    assertThrows<UnsupportedOperationException> { name = "AnotherName" }
    assertThrows<UnsupportedOperationException> { priority = 1 }
}
