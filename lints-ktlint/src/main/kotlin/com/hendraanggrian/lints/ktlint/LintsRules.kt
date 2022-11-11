package com.hendraanggrian.lints.ktlint

import com.hendraanggrian.lints.ktlint.kdoc.ParagraphContinuationFirstWordRule
import com.hendraanggrian.lints.ktlint.kdoc.TagDescriptionPunctuationRule
import com.pinterest.ktlint.core.RuleProvider
import com.pinterest.ktlint.core.RuleSetProviderV2

class LintsRules : RuleSetProviderV2(
    id = "lints-ktlint",
    about = About(
        maintainer = "Hendra Anggrian",
        description = "Personal Java/Kotlin linter rules",
        license = "The Apache License, Version 2.0",
        repositoryUrl = "https://github.com/hendraanggrian/lints/",
        issueTrackerUrl = "https://github.com/hendraanggrian/lints/issues/"
    )
) {
    override fun getRuleProviders(): Set<RuleProvider> = setOf(
        RuleProvider { FunctionSpecifyReturnTypeRule() },
        RuleProvider { ParagraphContinuationFirstWordRule() },
        RuleProvider { TagDescriptionPunctuationRule() }
    )
}
