package com.hanggrian.rulebook.checkstyle.checks

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test
import kotlin.test.assertFailsWith

class MeaninglessWordCheckTest : CheckTest() {
    override val check = MeaninglessWordCheck()

    @Test
    fun `Rule properties`() {
        check.assertProperties()

        check.setWords("Hello", "World")
        assertThat(check.wordSet).containsExactly("Hello", "World")
    }

    @Test
    fun `Meaningful class names`() = assertAll("MeaninglessWord1")

    @Test
    fun `Meaningless class names`() =
        assertAll(
            "MeaninglessWord2",
            "4:11: Avoid meaningless word 'Manager'.",
            "6:15: Avoid meaningless word 'Manager'.",
            "8:16: Avoid meaningless word 'Manager'.",
            "10:10: Avoid meaningless word 'Manager'.",
        )

    @Test
    fun `Allow meaningless prefix`() = assertAll("MeaninglessWord3")

    @Test
    fun `Utility class found`() =
        assertAll("MeaninglessWord4", "4:11: Rename utility class to 'Spaceships'.")
}
