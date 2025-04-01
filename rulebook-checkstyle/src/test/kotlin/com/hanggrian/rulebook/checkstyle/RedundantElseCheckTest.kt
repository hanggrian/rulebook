package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class RedundantElseCheckTest {
    private val checker = treeWalkerCheckerOf<RedundantElseCheck>()

    @Test
    fun `Rule properties`() = RedundantElseCheck().assertProperties()

    @Test
    fun `No throw or return in if`() = assertEquals(0, checker.read("RedundantElse1"))

    @Test
    fun `Lift else when if has return`() = assertEquals(2, checker.read("RedundantElse2"))

    @Test
    fun `Consider if-else without blocks`() = assertEquals(2, checker.read("RedundantElse3"))
}
