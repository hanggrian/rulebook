package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class UnnecessaryReturnCheckTest : CheckTest() {
    override val check = UnnecessaryReturnCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Function doesn't end with return`() = assertAll("UnnecessaryReturn1")

    @Test
    fun `Function end with return`() =
        assertAll("UnnecessaryReturn2", "6:9: Last 'return' is not needed.")

    @Test
    fun `Capture trailing non-return`() =
        assertAll("UnnecessaryReturn3", "6:9: Last 'return' is not needed.")

    @Test
    fun `Skip return statement with value`() = assertAll("UnnecessaryReturn4")
}
