package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class MemberOrderCheckTest : CheckTest() {
    override val check = MemberOrderCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Correct member organizations`() = assertAll("MemberOrder1")

    @Test
    fun `Member property after constructor`() =
        assertAll("MemberOrder2", "7:9: Arrange member 'property' before 'constructor'.")

    @Test
    fun `Member constructor after function`() =
        assertAll("MemberOrder3", "7:9: Arrange member 'constructor' before 'function'.")

    @Test
    fun `Skip static members`() = assertAll("MemberOrder4")
}
