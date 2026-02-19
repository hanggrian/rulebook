package com.hanggrian.rulebook.ktlint.rules

import com.google.common.truth.Truth.assertThat
import com.hanggrian.rulebook.ktlint.AllRules
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import com.pinterest.ktlint.ruleset.standard.StandardRuleSetProvider
import kotlin.test.Test

class RulebookRuleSetTest {
    @Test
    fun `All rules`() {
        assertThat(
            RulebookRuleSet()
                .getRuleProviders()
                .map { it.createNewRuleInstance().javaClass.kotlin },
        ).containsExactly(
            AbbreviationAsWordRule::class,
            BooleanPropertyInteropRule::class,
            BlockCommentClipRule::class,
            BlockCommentSpacesRule::class,
            BlockCommentTrimRule::class,
            BlockTagIndentationRule::class,
            BlockTagOrderRule::class,
            BlockTagPunctuationRule::class,
            BracesClipRule::class,
            CommonFunctionPositionRule::class,
            CommentTrimRule::class,
            ConfusingPredicateRule::class,
            DeprecatedTypeRule::class,
            DuplicateBlankLineInBlockCommentRule::class,
            DuplicateBlankLineInCommentRule::class,
            ElvisWrapRule::class,
            ExceptionInheritanceRule::class,
            FileSizeRule::class,
            GenericNameRule::class,
            IllegalThrowRule::class,
            IllegalVariableNameRule::class,
            InfixCallWrapRule::class,
            InnerClassPositionRule::class,
            LowercaseFRule::class,
            MeaninglessWordRule::class,
            MemberOrderRule::class,
            MissingBlankLineBeforeBlockTagsRule::class,
            MissingInlineInContractRule::class,
            NestedIfElseRule::class,
            NullEqualityRule::class,
            OverloadFunctionPositionRule::class,
            RedundantDefaultRule::class,
            RedundantElseRule::class,
            RootProjectNameRule::class,
            TodoCommentRule::class,
            UnnecessaryAbstractRule::class,
            UnnecessaryInitialBlankLineRule::class,
            UnnecessaryScopeRule::class,
            UnnecessarySwitchRule::class,
        )
    }

    @Test
    fun `No overlapping ID`() =
        assertThat(AllRules.ids)
            .containsNoneIn(StandardRuleSetProvider().getRuleProviders().ids)

    private val Set<RuleProvider>.ids
        get() =
            map {
                it
                    .createNewRuleInstance()
                    .ruleId.value
                    .substringAfterLast(':')
            }
}
