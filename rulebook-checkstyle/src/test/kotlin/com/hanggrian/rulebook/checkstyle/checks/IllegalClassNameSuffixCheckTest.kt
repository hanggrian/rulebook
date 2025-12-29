package com.hanggrian.rulebook.checkstyle.checks

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class IllegalClassNameSuffixCheckTest : CheckTest() {
    override val check = IllegalClassNameSuffixCheck()

    @Test
    fun `Rule properties`() {
        check.assertProperties()

        check.names = "Hello, World"
        assertThat(check.nameList).containsExactly("Hello", "World")
    }

    @Test
    fun `Meaningful class names`() = assertAll("IllegalClassNameSuffix1")

    @Test
    fun `Meaningless class names`() =
        assertAll(
            "IllegalClassNameSuffix2",
            "4:11: Avoid meaningless word 'Manager'.",
            "6:15: Avoid meaningless word 'Manager'.",
            "8:16: Avoid meaningless word 'Manager'.",
            "10:10: Avoid meaningless word 'Manager'.",
        )

    @Test
    fun `Utility class found`() =
        assertAll("IllegalClassNameSuffix3", "4:11: Rename utility class to 'Spaceships'.")
}
