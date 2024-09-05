package com.hanggrian.rulebook.checkstyle

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test
import kotlin.test.assertEquals

class GenericsNameAllowingCheckTest {
    private val checker = checkerOf<GenericsNameAllowingCheck>()

    @Test
    fun `Rule properties`() {
        val check = GenericsNameAllowingCheck()
        check.assertProperties()

        check.setNames("X", "Z")
        assertThat(check.names).containsExactly("X", "Z")
    }

    @Test
    fun `Common generic type in class-alike`() =
        assertEquals(0, checker.read("GenericsNameAllowing1"))

    @Test
    fun `Uncommon generic type in class-alike`() =
        assertEquals(2, checker.read("GenericsNameAllowing2"))

    @Test
    fun `Common generic type in function`() = assertEquals(0, checker.read("GenericsNameAllowing3"))

    @Test
    fun `Uncommon generic type in function`() =
        assertEquals(1, checker.read("GenericsNameAllowing4"))

    @Test
    fun `Skip inner generics`() = assertEquals(0, checker.read("GenericsNameAllowing5"))
}
