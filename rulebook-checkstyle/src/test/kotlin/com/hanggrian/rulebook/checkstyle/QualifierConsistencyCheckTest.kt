package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class QualifierConsistencyCheckTest {
    private val checker = checkerOf<QualifierConsistencyCheck>()

    @Test
    fun `Rule properties`() = QualifierConsistencyCheck().assertProperties()

    @Test
    fun `Consistent class qualifiers`() = assertEquals(0, checker.read("QualifierConsistency1"))

    @Test
    fun `Redundant class qualifiers`() = assertEquals(3, checker.read("QualifierConsistency2"))

    @Test
    fun `Consistent method qualifiers`() = assertEquals(0, checker.read("QualifierConsistency3"))

    @Test
    fun `Redundant method qualifiers`() = assertEquals(2, checker.read("QualifierConsistency4"))
}
