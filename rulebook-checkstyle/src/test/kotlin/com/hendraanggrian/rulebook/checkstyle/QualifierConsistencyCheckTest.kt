package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class QualifierConsistencyCheckTest {
    private val checker = checkerOf<QualifierConsistencyCheck>()

    @Test
    fun `Rule properties()`(): Unit = QualifierConsistencyCheck().assertProperties()

    @Test
    fun `Consistent qualifiers`() = assertEquals(0, checker.read("QualifierConsistencyCheck1"))

    @Test
    fun `Redundant qualifiers`() = assertEquals(3, checker.read("QualifierConsistencyCheck2"))
}
