package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider

public class RulebookRuleSet : RuleSetProviderV3(Rule.ID) {
    override fun getRuleProviders(): Set<RuleProvider> =
        setOf(
            RuleProvider { BlockCommentLineJoiningRule() },
            RuleProvider { BlockCommentLineTrimmingRule() },
            RuleProvider { BlockCommentSpacingRule() },
            RuleProvider { BlockTagInitialLineSpacingRule() },
            RuleProvider { BlockTagOrderingRule() },
            RuleProvider { BlockTagPunctuationRule() },
            RuleProvider { CaseLineJoiningRule() },
            RuleProvider { ClassFinalNameDisallowingRule() },
            RuleProvider { ClassMemberOrderingRule() },
            RuleProvider { ClassNameAcronymCapitalizationRule() },
            RuleProvider { CommentLineJoiningRule() },
            RuleProvider { CommentLineTrimmingRule() },
            RuleProvider { DefaultDenestingRule() },
            RuleProvider { ElseDenestingRule() },
            RuleProvider { ElvisWrappingRule() },
            RuleProvider { EmptyCodeBlockUnwrappingRule() },
            RuleProvider { ExceptionExtendingRule() },
            RuleProvider { ExceptionSubclassThrowingRule() },
            RuleProvider { FileInitialLineTrimmingRule() },
            RuleProvider { FileSizeLimitationRule() },
            RuleProvider { GenericsNameAllowingRule() },
            RuleProvider { IdentifierNameDisallowingRule() },
            RuleProvider { IfElseDenestingRule() },
            RuleProvider { InfixCallWrappingRule() },
            RuleProvider { InnerClassPositionRule() },
            RuleProvider { KotlinPropertyNameInteroperabilityRule() },
            RuleProvider { KotlinTypePriorityRule() },
            RuleProvider { NullStructuralEqualityRule() },
            RuleProvider { OverloadFunctionPositionRule() },
            RuleProvider { SpecialFunctionPositionRule() },
            RuleProvider { SwitchCaseBranchingRule() },
            RuleProvider { TodoCommentStylingRule() },
        )
}
