package com.hanggrian.rulebook.codenarc

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class StringsTest {
    @Test
    fun trimComment() = assertThat("int i = 0 // comment".trimComment()).isEqualTo("int i = 0")
}
