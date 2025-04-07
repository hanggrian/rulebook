package com.hanggrian.rulebook.ktlint

import com.google.common.truth.Truth.assertThat
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
            AbstractClassDefinitionRule::class,
            BlockCommentSpacesRule::class,
            BlockCommentTrimRule::class,
            BlockTagIndentationRule::class,
            BlockTagOrderRule::class,
            BlockTagPunctuationRule::class,
            BuiltInFunctionPositionRule::class,
            BuiltInTypesRule::class,
            CaseSeparatorRule::class,
            ClassNameAcronymRule::class,
            CommentTrimRule::class,
            ConfusingPredicateRule::class,
            ContractFunctionDefinitionRule::class,
            DuplicateBlankLineInBlockCommentRule::class,
            DuplicateBlankLineInCommentRule::class,
            ElvisWrapRule::class,
            EmptyCodeBlockJoinRule::class,
            ExceptionInheritanceRule::class,
            FileSizeRule::class,
            IllegalClassFinalNameRule::class,
            IllegalThrowRule::class,
            IllegalVariableNameRule::class,
            InfixCallWrapRule::class,
            InnerClassPositionRule::class,
            MemberOrderRule::class,
            MissingBlankLineBeforeBlockTagsRule::class,
            NestedIfElseRule::class,
            NullEqualityRule::class,
            NumberSuffixForFloatRule::class,
            OverloadFunctionPositionRule::class,
            PropertyNameInteropRule::class,
            RedundantDefaultRule::class,
            RedundantElseRule::class,
            RequiredGenericNameRule::class,
            ShortBlockCommentJoinRule::class,
            TodoCommentRule::class,
            UnnecessaryBlankLineBeforePackageRule::class,
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
