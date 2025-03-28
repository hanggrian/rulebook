package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class RedundantQualifierCheckTest {
    private val checker = treeWalkerCheckerOf<RedundantQualifierCheck>()

    @Test
    fun `Rule properties`() = RedundantQualifierCheck().assertProperties()

    @Test
    fun `Consistent class qualifiers`() = assertEquals(0, checker.read("RedundantQualifier1"))

    @Test
    fun `Redundant class qualifiers`() = assertEquals(3, checker.read("RedundantQualifier2"))

    @Test
    fun `Consistent method qualifiers`() = assertEquals(0, checker.read("RedundantQualifier3"))

    @Test
    fun `Redundant method qualifiers`() = assertEquals(2, checker.read("RedundantQualifier4"))
}
