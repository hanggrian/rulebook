package com.hanggrian.rulebook.ktlint

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class CollectionsTest {
    @Test
    fun twoWayMapOf() {
        val map = twoWayMapOf('a' to 'b')
        assertThat(map).containsEntry('a', 'b')
        assertThat(map).containsEntry('b', 'a')
    }
}
