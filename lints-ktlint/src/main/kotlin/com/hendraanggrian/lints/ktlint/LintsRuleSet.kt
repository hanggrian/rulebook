package com.hendraanggrian.lints.ktlint

import com.hendraanggrian.lints.ktlint.kdoc.SummaryContinuationFirstWordRule
import com.hendraanggrian.lints.ktlint.kdoc.TagDescriptionSentenceRule
import com.hendraanggrian.lints.ktlint.kdoc.TagGroupStartingEmptyLineRule
import com.pinterest.ktlint.core.RuleProvider
import com.pinterest.ktlint.core.RuleSetProviderV2

class LintsRuleSet : RuleSetProviderV2(
    id = "lints",
    about = About(
        maintainer = "Hendra Anggrian",
        description = "Personal linter rules and code convention",
        license = "The Apache License, Version 2.0",
        repositoryUrl = "https://github.com/hendraanggrian/lints/",
        issueTrackerUrl = "https://github.com/hendraanggrian/lints/issues/"
    )
) {
    override fun getRuleProviders(): Set<RuleProvider> = setOf(
        RuleProvider { ExceptionAmbiguityRule() },
        RuleProvider { FunctionSpecifyReturnTypeRule() },
        RuleProvider { SummaryContinuationFirstWordRule() },
        RuleProvider { TagDescriptionSentenceRule() },
        RuleProvider { TagGroupStartingEmptyLineRule() }
    )
}
