package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class FileInitialJoiningCheckTest {
    private val checker = checkerOf<FileInitialJoiningCheck>()

    @Test
    fun `Rule properties()`(): Unit = FileInitialJoiningCheck().assertProperties()

    @Test
    fun `Trimmed file`() = assertEquals(0, checker.read("FileInitialJoining1"))

    @Test
    fun `Padded file`() = assertEquals(1, checker.read("FileInitialJoining2"))

    @Test
    fun `Skip comment`() = assertEquals(0, checker.read("FileInitialJoining3"))
}
