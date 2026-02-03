package com.hanggrian.rulebook.checkstyle.checks

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class MeaninglessWordCheckTest : CheckTest() {
    override val check = MeaninglessWordCheck()

    @Test
    fun `Rule properties`() {
        check.assertProperties()

        check.words = "Hello, World"
        assertThat(check.wordList).containsExactly("Hello", "World")
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
    fun `Utility class found`() =
        assertAll("MeaninglessWord3", "4:11: Rename utility class to 'Spaceships'.")
}
