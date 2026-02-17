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
            UnnecessaryAbstractRule::class,
            BlockCommentSpacesRule::class,
            BlockCommentTrimRule::class,
            BlockTagIndentationRule::class,
            BlockTagOrderRule::class,
            BlockTagPunctuationRule::class,
            CommonFunctionPositionRule::class,
            DeprecatedTypeRule::class,
            AbbreviationAsWordRule::class,
            CommentTrimRule::class,
            ConfusingPredicateRule::class,
            MissingInlineInContractRule::class,
            DuplicateBlankLineInBlockCommentRule::class,
            DuplicateBlankLineInCommentRule::class,
            ElvisWrapRule::class,
            BracesClipRule::class,
            ExceptionInheritanceRule::class,
            FileSizeRule::class,
            MeaninglessWordRule::class,
            IllegalThrowRule::class,
            IllegalVariableNameRule::class,
            InfixCallWrapRule::class,
            InnerClassPositionRule::class,
            MemberOrderRule::class,
            MissingBlankLineBeforeBlockTagsRule::class,
            NestedIfElseRule::class,
            NullEqualityRule::class,
            LowercaseFRule::class,
            OverloadFunctionPositionRule::class,
            BooleanPropertyInteropRule::class,
            RedundantDefaultRule::class,
            RedundantElseRule::class,
            GenericNameRule::class,
            BlockCommentClipRule::class,
            TodoCommentRule::class,
            UnnecessaryInitialBlankLineRule::class,
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
