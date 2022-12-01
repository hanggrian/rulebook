package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.kdoc.SummaryContinuationRule
import com.hendraanggrian.rulebook.ktlint.kdoc.TagDescriptionSentenceRule
import com.hendraanggrian.rulebook.ktlint.kdoc.TagsStartingEmptyLineRule
import com.pinterest.ktlint.core.RuleProvider
import com.pinterest.ktlint.core.RuleSetProviderV2

class RulebookRuleSet : RuleSetProviderV2(
    id = "rulebook",
    about = About(
        maintainer = "Hendra Anggrian",
        description = "Personal linter rules and code convention",
        license = "https://github.com/hendraanggrian/rulebook/blob/master/LICENSE",
        repositoryUrl = "https://github.com/hendraanggrian/rulebook/",
        issueTrackerUrl = "https://github.com/hendraanggrian/rulebook/issues/"
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
