package com.hanggrian.rulebook.checkstyle.checks

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class MemberOrderCheckTest : CheckTest() {
    override val check = MemberOrderCheck()

    @Test
    fun `Rule properties`() {
        check.assertProperties()

        check.order = "constructor, property, static, method"
        assertThat(check.orderList).containsExactly("constructor", "property", "static", "method")
    }

    @Test
    fun `Correct member organizations`() = assertAll("MemberOrder1")

    @Test
    fun `Property after constructor`() =
        assertAll("MemberOrder2", "7:9: Arrange member 'property' before 'constructor'.")

    @Test
    fun `Constructor after function`() =
        assertAll("MemberOrder3", "7:9: Arrange member 'constructor' before 'function'.")

    @Test
    fun `Function after static member`() =
        assertAll("MemberOrder4", "7:9: Arrange member 'function' before 'static member'.")
}
