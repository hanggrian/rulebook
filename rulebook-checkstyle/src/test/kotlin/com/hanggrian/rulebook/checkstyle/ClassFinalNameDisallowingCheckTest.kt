package com.hanggrian.rulebook.checkstyle

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test
import kotlin.test.assertEquals

class ClassFinalNameDisallowingCheckTest {
    private val checker = checkerOf<ClassFinalNameDisallowingCheck>()

    @Test
    fun `Rule properties`() {
        val check = ClassFinalNameDisallowingCheck()
        check.assertProperties()

        check.setNames("Hello", "World")
        assertThat(check.names).containsExactly("Hello", "World")
    }

    @Test
    fun `Meaningful class names`() = assertEquals(0, checker.read("ClassFinalNameDisallowing1"))

    @Test
    fun `Meaningless class names`() = assertEquals(4, checker.read("ClassFinalNameDisallowing2"))

    @Test
    fun `Utility class found`() = assertEquals(1, checker.read("ClassFinalNameDisallowing3"))
}
