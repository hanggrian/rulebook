package com.hendraanggrian.codestyle.ktlint

import com.pinterest.ktlint.core.RuleProvider
import com.pinterest.ktlint.core.RuleSetProviderV2

class KtlintCodestyleRules : RuleSetProviderV2(
    id = "codestyle-ktlint",
    about = About(
        maintainer = "Hendra Anggrian",
        description = "Personal Kotlin code convention and rules",
        license = "The Apache License, Version 2.0",
        repositoryUrl = "https://github.com/hendraanggrian/ktlint-rules/",
        issueTrackerUrl = "https://github.com/hendraanggrian/ktlint-rules/issues/"
    )
) {
    override fun getRuleProviders(): Set<RuleProvider> = setOf(
        RuleProvider { SpecifyReturnTypeRule() },
        RuleProvider { KDocParagraphRule() }
    )
}
