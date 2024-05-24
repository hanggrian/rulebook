package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ClassNameBlacklistingCheckTest {
    private val checker = checkerOf<ClassNameBlacklistingCheck>()

    @Test
    fun `Rule properties()`(): Unit = ClassNameBlacklistingCheck().assertProperties()

    @Test
    fun `Meaningful class names`() = assertEquals(0, checker.read("ClassNameBlacklisting1"))

    @Test
    fun `Meaningless class names`() = assertEquals(4, checker.read("ClassNameBlacklisting2"))

    @Test
    fun `Utility class found`() = assertEquals(1, checker.read("ClassNameBlacklisting3"))
}
