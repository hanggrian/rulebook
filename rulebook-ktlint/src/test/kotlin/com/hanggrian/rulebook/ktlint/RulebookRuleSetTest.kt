package com.hanggrian.rulebook.ktlint

import com.google.common.truth.Truth.assertThat
import com.hanggrian.rulebook.ktlint.rules.AbbreviationAsWordRule
import com.hanggrian.rulebook.ktlint.rules.BlockCommentClipRule
import com.hanggrian.rulebook.ktlint.rules.BlockCommentSpacesRule
import com.hanggrian.rulebook.ktlint.rules.BlockCommentTrimRule
import com.hanggrian.rulebook.ktlint.rules.BlockTagIndentationRule
import com.hanggrian.rulebook.ktlint.rules.BlockTagOrderRule
import com.hanggrian.rulebook.ktlint.rules.BlockTagPunctuationRule
import com.hanggrian.rulebook.ktlint.rules.BooleanPropertyInteropRule
import com.hanggrian.rulebook.ktlint.rules.BracesClipRule
import com.hanggrian.rulebook.ktlint.rules.CommentTrimRule
import com.hanggrian.rulebook.ktlint.rules.CommonFunctionPositionRule
import com.hanggrian.rulebook.ktlint.rules.ConfusingPredicateRule
import com.hanggrian.rulebook.ktlint.rules.DecentralizedDependencyRule
import com.hanggrian.rulebook.ktlint.rules.DeprecatedTypeRule
import com.hanggrian.rulebook.ktlint.rules.DuplicateBlankLineInBlockCommentRule
import com.hanggrian.rulebook.ktlint.rules.DuplicateBlankLineInCommentRule
import com.hanggrian.rulebook.ktlint.rules.EagerApiRule
import com.hanggrian.rulebook.ktlint.rules.ElvisWrapRule
import com.hanggrian.rulebook.ktlint.rules.FileSizeRule
import com.hanggrian.rulebook.ktlint.rules.GenericNameRule
import com.hanggrian.rulebook.ktlint.rules.IllegalThrowRule
import com.hanggrian.rulebook.ktlint.rules.IllegalVariableNameRule
import com.hanggrian.rulebook.ktlint.rules.InfixCallWrapRule
import com.hanggrian.rulebook.ktlint.rules.InnerClassPositionRule
import com.hanggrian.rulebook.ktlint.rules.InternalErrorRule
import com.hanggrian.rulebook.ktlint.rules.LonelyCaseRule
import com.hanggrian.rulebook.ktlint.rules.LonelyIfRule
import com.hanggrian.rulebook.ktlint.rules.LowercaseFRule
import com.hanggrian.rulebook.ktlint.rules.LowercaseHexRule
import com.hanggrian.rulebook.ktlint.rules.MeaninglessWordRule
import com.hanggrian.rulebook.ktlint.rules.MemberOrderRule
import com.hanggrian.rulebook.ktlint.rules.MissingBlankLineBeforeBlockTagsRule
import com.hanggrian.rulebook.ktlint.rules.MissingInlineInContractRule
import com.hanggrian.rulebook.ktlint.rules.NestedIfElseRule
import com.hanggrian.rulebook.ktlint.rules.NullEqualityRule
import com.hanggrian.rulebook.ktlint.rules.OverloadFunctionPositionRule
import com.hanggrian.rulebook.ktlint.rules.RedundantDefaultRule
import com.hanggrian.rulebook.ktlint.rules.RedundantElseRule
import com.hanggrian.rulebook.ktlint.rules.RedundantIfRule
import com.hanggrian.rulebook.ktlint.rules.RootProjectNameRule
import com.hanggrian.rulebook.ktlint.rules.ScriptFileNameRule
import com.hanggrian.rulebook.ktlint.rules.TodoCommentRule
import com.hanggrian.rulebook.ktlint.rules.UnnecessaryAbstractRule
import com.hanggrian.rulebook.ktlint.rules.UnnecessaryContinueRule
import com.hanggrian.rulebook.ktlint.rules.UnnecessaryInitialBlankLineRule
import com.hanggrian.rulebook.ktlint.rules.UnnecessaryReturnRule
import com.hanggrian.rulebook.ktlint.rules.UnnecessaryScopeRule
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import com.pinterest.ktlint.ruleset.standard.StandardRuleSetProvider
import kotlin.test.Test

class RulebookRuleSetTest {
    @Test
    fun `All rules`() {
        assertThat(
            AllRules
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
            DecentralizedDependencyRule::class,
            DeprecatedTypeRule::class,
            DuplicateBlankLineInBlockCommentRule::class,
            DuplicateBlankLineInCommentRule::class,
            EagerApiRule::class,
            ElvisWrapRule::class,
            FileSizeRule::class,
            GenericNameRule::class,
            IllegalThrowRule::class,
            IllegalVariableNameRule::class,
            InfixCallWrapRule::class,
            InnerClassPositionRule::class,
            InternalErrorRule::class,
            LonelyCaseRule::class,
            LonelyIfRule::class,
            LowercaseFRule::class,
            LowercaseHexRule::class,
            MeaninglessWordRule::class,
            MemberOrderRule::class,
            MissingBlankLineBeforeBlockTagsRule::class,
            MissingInlineInContractRule::class,
            NestedIfElseRule::class,
            NullEqualityRule::class,
            OverloadFunctionPositionRule::class,
            RedundantDefaultRule::class,
            RedundantElseRule::class,
            RedundantIfRule::class,
            RootProjectNameRule::class,
            ScriptFileNameRule::class,
            TodoCommentRule::class,
            UnnecessaryAbstractRule::class,
            UnnecessaryContinueRule::class,
            UnnecessaryInitialBlankLineRule::class,
            UnnecessaryReturnRule::class,
            UnnecessaryScopeRule::class,
        )
    }

    @Test
    fun `No overlapping ID`() {
        assertThat(AllRules.ids.distinct().size)
            .isEqualTo(AllRules.ids.size)

        assertThat(AllRules.ids)
            .containsNoneIn(StandardRuleSetProvider().getRuleProviders().ids)
    }

    private val Set<RuleProvider>.ids
        get() =
            map {
                it
                    .createNewRuleInstance()
                    .ruleId
                    .value
                    .substringAfterLast(':')
            }
}
