package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class NoUselessReturnCheckTest : CheckTest() {
    override val check = NoUselessReturnCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Function doesn't end with return`() = assertAll("NoUselessReturn1")

    @Test
    fun `Function end with return`() =
        assertAll("NoUselessReturn2", "6:9: Last 'return' is not needed.")

    @Test
    fun `Capture trailing non-return`() =
        assertAll("NoUselessReturn3", "6:9: Last 'return' is not needed.")

    @Test
    fun `Skip return statement with value`() = assertAll("NoUselessReturn4")
}
