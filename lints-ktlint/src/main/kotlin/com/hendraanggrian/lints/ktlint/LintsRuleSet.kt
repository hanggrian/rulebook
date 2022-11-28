package com.hendraanggrian.lints.ktlint

import com.hendraanggrian.lints.ktlint.kdoc.SummaryContinuationRule
import com.hendraanggrian.lints.ktlint.kdoc.TagDescriptionSentenceRule
import com.hendraanggrian.lints.ktlint.kdoc.TagsStartingEmptyLineRule
import com.pinterest.ktlint.core.RuleProvider
import com.pinterest.ktlint.core.RuleSetProviderV2

class LintsRuleSet : RuleSetProviderV2(
    id = "lints-ktlint",
    about = About(
        maintainer = "Hendra Anggrian",
        description = "Personal linter rules and code convention",
        license = "https://github.com/hendraanggrian/lints/blob/master/LICENSE",
        repositoryUrl = "https://github.com/hendraanggrian/lints/",
        issueTrackerUrl = "https://github.com/hendraanggrian/lints/issues/"
    )
) {
    override fun getRuleProviders(): Set<RuleProvider> = setOf(
        RuleProvider { SummaryContinuationRule() },
        RuleProvider { TagDescriptionSentenceRule() },
        RuleProvider { TagsStartingEmptyLineRule() },
        RuleProvider { ExceptionAmbiguityRule() },
        RuleProvider { FilenameAcronymRule() },
        RuleProvider { FunctionReturnTypeRule() },
        RuleProvider { TypeKotlinApiRule() }
    )
}
