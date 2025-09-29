package com.hanggrian.rulebook.ktlint

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class MessagesTest {
    @Test
    fun get() {
        assertThat(Messages["block.tag.punctuation"])
            .isEqualTo("End ''{0}'' with a period.")
        assertThat(Messages.get("block.tag.punctuation", "stub"))
            .isEqualTo("End 'stub' with a period.")
    }
}
