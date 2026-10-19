package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class NoRedundantDefaultCheckTest : CheckTest() {
    override val check = NoRedundantDefaultCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `No throw or return in case`() = assertAll("NoRedundantDefault1")

    @Test
    fun `Lift else when case has return`() =
        assertAll("NoRedundantDefault2", "10:13: Omit redundant 'default' condition.")

    @Test
    fun `Skip if not all case blocks have jump statement`() = assertAll("NoRedundantDefault3")

    @Test
    fun `Skip only default case`() = assertAll("NoRedundantDefault4")
}
