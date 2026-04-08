package com.hanggrian.rulebook.ktlint.rules

import com.pinterest.ktlint.test.KtLintAssertThat
import kotlin.test.assertEquals
import kotlin.test.assertFalse

open class RuleTest {
    fun KtLintAssertThat.asScript() = asFileWithPath("test.kts")

    fun KtLintAssertThat.asTest() = asFileWithPath("test/SomeTest.kt")

    inline fun <reified T : RulebookRule> T.assertProperties() {
        assertEquals(
            "rulebook:" +
                T::class
                    .java
                    .simpleName
                    .substringBefore("Rule")
                    .replace(Regex("([a-z])([A-Z])"), "$1-$2")
                    .lowercase(),
            ruleId.value,
        )
        assertFalse(about.maintainer.isBlank())
        assertFalse(about.repositoryUrl.isBlank())
        assertFalse(about.issueTrackerUrl.isBlank())
    }
}
