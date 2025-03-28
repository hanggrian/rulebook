package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class RedundantDefaultCheckTest {
    private val checker = treeWalkerCheckerOf<RedundantDefaultCheck>()

    @Test
    fun `Rule properties`() = RedundantDefaultCheck().assertProperties()

    @Test
    fun `No throw or return in case`() = assertEquals(0, checker.read("RedundantDefault1"))

    @Test
    fun `Lift else when case has return`() = assertEquals(1, checker.read("RedundantDefault2"))

    @Test
    fun `Skip if not all case blocks have jump statement`() =
        assertEquals(0, checker.read("RedundantDefault3"))
}
