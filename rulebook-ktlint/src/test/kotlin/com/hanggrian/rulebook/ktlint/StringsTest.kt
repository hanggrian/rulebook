package com.hanggrian.rulebook.ktlint

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class StringsTest {
    @Test
    fun qualifierName() {
        assertThat("String?".qualifierName).isEqualTo("String")
        assertThat("List<String>".qualifierName).isEqualTo("List")
    }
}
