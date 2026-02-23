package com.hanggrian.rulebook.internal

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import com.pinterest.ktlint.rule.engine.core.api.RuleSetId

class CodecheckRuleSet : RuleSetProviderV3(ID) {
    override fun getRuleProviders(): Set<RuleProvider> =
        setOf(
            RuleProvider { UrlInDocumentationRule() },
        )

    internal companion object {
        val ID: RuleSetId = RuleSetId("codecheck")
    }
}
