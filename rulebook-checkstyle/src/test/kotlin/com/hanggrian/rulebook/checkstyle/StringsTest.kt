package com.hanggrian.rulebook.checkstyle

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class StringsTest {
    @Test
    fun splitToList() {
        assertThat((null as String?).splitToList()).isEmpty()
        assertThat("Lorem".splitToList()).containsExactly("Lorem")
        assertThat("Lorem, Ipsum".splitToList()).containsExactly("Lorem", "Ipsum")
    }
}
