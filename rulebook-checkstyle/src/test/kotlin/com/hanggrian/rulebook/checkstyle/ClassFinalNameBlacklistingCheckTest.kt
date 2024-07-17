package com.hanggrian.rulebook.checkstyle

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test
import kotlin.test.assertEquals

class ClassFinalNameBlacklistingCheckTest {
    private val checker = checkerOf<ClassFinalNameBlacklistingCheck>()

    @Test
    fun `Rule properties`() {
        val check = ClassFinalNameBlacklistingCheck()
        check.assertProperties()

        check.setNames("Hello", "World")
        assertThat(check.names).containsExactly("Hello", "World")
    }

    @Test
    fun `Meaningful class names`() = assertEquals(0, checker.read("ClassNameBlacklisting1"))

    @Test
    fun `Meaningless class names`() = assertEquals(4, checker.read("ClassNameBlacklisting2"))

    @Test
    fun `Utility class found`() = assertEquals(1, checker.read("ClassNameBlacklisting3"))
}
