package com.hendraanggrian.rulebook.ktlint

import kotlin.test.assertEquals
import kotlin.test.assertFalse

internal inline fun <reified T : Rule> T.assertProperties() {
    assertEquals(
        "rulebook:" +
            T::class.java.simpleName.substringBefore("Rule")
                .replace(Regex("([a-z])([A-Z])"), "$1-$2")
                .lowercase(),
        ruleId.value,
    )
    assertFalse(about.maintainer.isBlank())
    assertFalse(about.repositoryUrl.isBlank())
    assertFalse(about.issueTrackerUrl.isBlank())
}
