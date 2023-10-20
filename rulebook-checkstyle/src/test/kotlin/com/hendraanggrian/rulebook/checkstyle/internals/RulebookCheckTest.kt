package com.hendraanggrian.rulebook.checkstyle.internals

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.rulebook.checkstyle.RenameMeaninglessWordCheck
import kotlin.test.Test

class RulebookCheckTest {
    @Test
    fun `Compare tokens`() {
        val check = RenameMeaninglessWordCheck()
        val requiredTokens = check.requiredTokens.asList()

        assertThat(check.defaultTokens).asList().containsExactlyElementsIn(requiredTokens)
        assertThat(check.acceptableTokens).asList().containsExactlyElementsIn(requiredTokens)
    }
}
