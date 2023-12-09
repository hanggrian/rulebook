package com.hendraanggrian.rulebook.checkstyle

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class RulebookCheckTest {
    @Test
    fun `Compare tokens`() {
        val check = SourceWordMeaningCheck()
        val requiredTokens = check.requiredTokens.asList()

        assertThat(check.defaultTokens).asList().containsExactlyElementsIn(requiredTokens)
        assertThat(check.acceptableTokens).asList().containsExactlyElementsIn(requiredTokens)
    }
}
