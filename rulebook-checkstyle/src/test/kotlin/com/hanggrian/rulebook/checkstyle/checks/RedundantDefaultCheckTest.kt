package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class RedundantDefaultCheckTest : CheckTest() {
    override val check = RedundantDefaultCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `No throw or return in case`() = assertAll("RedundantDefault1")

    @Test
    fun `Lift else when case has return`() =
        assertAll("RedundantDefault2", "10:13: Omit redundant 'default' condition.")

    @Test
    fun `Skip if not all case blocks have jump statement`() = assertAll("RedundantDefault3")
}
