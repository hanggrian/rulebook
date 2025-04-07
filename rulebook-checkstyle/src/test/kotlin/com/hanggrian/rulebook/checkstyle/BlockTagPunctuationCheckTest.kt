package com.hanggrian.rulebook.checkstyle

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class BlockTagPunctuationCheckTest : CheckTest() {
    override val check = BlockTagPunctuationCheck()

    @Test
    fun `Rule properties`() {
        check.assertProperties()

        check.setTags("@author", "@see")
        assertThat(check.tags).containsExactly("@author", "@see")
    }

    @Test
    fun `No description`() = assertAll("BlockTagPunctuation1")

    @Test
    fun `Descriptions end with a period`() = assertAll("BlockTagPunctuation2")

    @Test
    fun `Descriptions don't end with a period`() =
        assertAll(
            "BlockTagPunctuation3",
            "5:19: End '@param' with a period.",
            "6:16: End '@return' with a period.",
        )

    @Test
    fun `Long Descriptions`() =
        assertAll(
            "BlockTagPunctuation4",
            "5:19: End '@param' with a period.",
            "7:7: End '@return' with a period.",
        )
}
