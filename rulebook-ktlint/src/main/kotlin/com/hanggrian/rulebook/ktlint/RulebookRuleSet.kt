package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import com.pinterest.ktlint.rule.engine.core.api.RuleSetId

public class RulebookRuleSet : RuleSetProviderV3(ID) {
    override fun getRuleProviders(): Set<RuleProvider> =
        setOf(
            RuleProvider { AbstractClassFunctionAbstractionRule() },
            RuleProvider { BlockCommentLineJoiningRule() },
            RuleProvider { BlockCommentLineTrimmingRule() },
            RuleProvider { BlockCommentSignatureRule() },
            RuleProvider { BlockCommentSpacingRule() },
            RuleProvider { BlockTagIndentationRule() },
            RuleProvider { BlockTagInitialLineSpacingRule() },
            RuleProvider { BlockTagOrderingRule() },
            RuleProvider { BlockTagPunctuationRule() },
            RuleProvider { BuiltinFunctionPositionRule() },
            RuleProvider { BuiltinTypePriorityRule() },
            RuleProvider { CaseLineSpacingRule() },
            RuleProvider { ClassExceptionExtendingRule() },
            RuleProvider { ClassFinalNameDisallowingRule() },
            RuleProvider { ClassMemberOrderingRule() },
            RuleProvider { ClassNameAcronymCapitalizationRule() },
            RuleProvider { CommentLineJoiningRule() },
            RuleProvider { CommentLineTrimmingRule() },
            RuleProvider { ContractFunctionInliningRule() },
            RuleProvider { DefaultFlatteningRule() },
            RuleProvider { ElseFlatteningRule() },
            RuleProvider { ElvisWrappingRule() },
            RuleProvider { EmptyCodeBlockUnwrappingRule() },
            RuleProvider { ExceptionSubclassThrowingRule() },
            RuleProvider { FileInitialLineTrimmingRule() },
            RuleProvider { FileSizeLimitationRule() },
            RuleProvider { FloatSuffixLowercasingRule() },
            RuleProvider { GenericsNameAllowingRule() },
            RuleProvider { IfElseFlatteningRule() },
            RuleProvider { InfixCallWrappingRule() },
            RuleProvider { InnerClassPositionRule() },
            RuleProvider { NullStructuralEqualityRule() },
            RuleProvider { OverloadFunctionPositionRule() },
            RuleProvider { PredicateCallPositivityRule() },
            RuleProvider { PropertyNameInteroperabilityRule() },
            RuleProvider { SwitchCaseBranchingRule() },
            RuleProvider { TodoCommentFormattingRule() },
            RuleProvider { VariableNameDisallowingRule() },
        )

    internal companion object {
        val ID: RuleSetId = RuleSetId("rulebook")
    }
}
