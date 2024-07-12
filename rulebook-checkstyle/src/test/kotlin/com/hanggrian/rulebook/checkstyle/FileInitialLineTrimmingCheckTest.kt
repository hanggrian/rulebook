package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class FileInitialLineTrimmingCheckTest {
    private val checker = checkerOf<FileInitialLineTrimmingCheck>()

    @Test
    fun `Rule properties`() = FileInitialLineTrimmingCheck().assertProperties()

    @Test
    fun `Trimmed file`() = assertEquals(0, checker.read("FileInitialLineTrimming1"))

    @Test
    fun `Padded file`() = assertEquals(1, checker.read("FileInitialLineTrimming2"))

    @Test
    fun `Skip comment`() = assertEquals(0, checker.read("FileInitialLineTrimming3"))
}
