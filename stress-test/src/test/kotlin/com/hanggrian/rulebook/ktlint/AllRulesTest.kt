package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.JvmBasedTest
import com.pinterest.ktlint.ruleset.standard.rules.AnnotationRule
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRuleBuilder

open class AllRulesTest : JvmBasedTest {
    protected val assertThatCodeAll by lazy {
        assertThatRuleBuilder { AnnotationRule() }
            .let {
                var builder = it
                for (rule in RulebookRuleSet().getRuleProviders()) {
                    builder = builder.addAdditionalRuleProvider { rule.createNewRuleInstance() }
                }
                builder
            }.assertThat()
    }
}
