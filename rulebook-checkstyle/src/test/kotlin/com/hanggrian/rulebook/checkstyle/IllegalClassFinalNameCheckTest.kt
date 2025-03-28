package com.hanggrian.rulebook.checkstyle

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test
import kotlin.test.assertEquals

class IllegalClassFinalNameCheckTest {
    private val checker = treeWalkerCheckerOf<IllegalClassFinalNameCheck>()

    @Test
    fun `Rule properties`() {
        val check = IllegalClassFinalNameCheck()
        check.assertProperties()

        check.setNames("Hello", "World")
        assertThat(check.names).containsExactly("Hello", "World")
    }

    @Test
    fun `Meaningful class names`() = assertEquals(0, checker.read("IllegalClassFinalName1"))

    @Test
    fun `Meaningless class names`() = assertEquals(4, checker.read("IllegalClassFinalName2"))

    @Test
    fun `Utility class found`() = assertEquals(1, checker.read("IllegalClassFinalName3"))
}
