package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.JvmBasedTest
import com.hanggrian.rulebook.checkstyle.checks.AbstractFunctionInAbstractClassCheck
import com.hanggrian.rulebook.checkstyle.checks.AssignmentWrapCheck
import com.hanggrian.rulebook.checkstyle.checks.BlockCommentTrimCheck
import com.hanggrian.rulebook.checkstyle.checks.BlockTagPunctuationCheck
import com.hanggrian.rulebook.checkstyle.checks.BracesTrimCheck
import com.hanggrian.rulebook.checkstyle.checks.CaseSeparatorCheck
import com.hanggrian.rulebook.checkstyle.checks.ChainCallWrapCheck
import com.hanggrian.rulebook.checkstyle.checks.CommentSpacesCheck
import com.hanggrian.rulebook.checkstyle.checks.CommentTrimCheck
import com.hanggrian.rulebook.checkstyle.checks.CommonFunctionPositionCheck
import com.hanggrian.rulebook.checkstyle.checks.ComplicatedAssignmentCheck
import com.hanggrian.rulebook.checkstyle.checks.DuplicateBlankLineCheck
import com.hanggrian.rulebook.checkstyle.checks.DuplicateBlankLineInBlockCommentCheck
import com.hanggrian.rulebook.checkstyle.checks.DuplicateBlankLineInCommentCheck
import com.hanggrian.rulebook.checkstyle.checks.ExceptionInheritanceCheck
import com.hanggrian.rulebook.checkstyle.checks.LambdaWrapCheck
import com.hanggrian.rulebook.checkstyle.checks.LonelyCaseCheck
import com.hanggrian.rulebook.checkstyle.checks.LonelyIfCheck
import com.hanggrian.rulebook.checkstyle.checks.LowercaseDCheck
import com.hanggrian.rulebook.checkstyle.checks.LowercaseFCheck
import com.hanggrian.rulebook.checkstyle.checks.LowercaseHexCheck
import com.hanggrian.rulebook.checkstyle.checks.MeaninglessWordCheck
import com.hanggrian.rulebook.checkstyle.checks.MemberOrderCheck
import com.hanggrian.rulebook.checkstyle.checks.NestedIfElseCheck
import com.hanggrian.rulebook.checkstyle.checks.NoParenthesesInLambdaCheck
import com.hanggrian.rulebook.checkstyle.checks.NoRedundantQualifierCheck
import com.hanggrian.rulebook.checkstyle.checks.ParameterWrapCheck
import com.hanggrian.rulebook.checkstyle.checks.ParenthesesTrimCheck
import com.hanggrian.rulebook.checkstyle.checks.RedundantDefaultCheck
import com.hanggrian.rulebook.checkstyle.checks.RedundantElseCheck
import com.hanggrian.rulebook.checkstyle.checks.SimpleSizeComparisonCheck
import com.hanggrian.rulebook.checkstyle.checks.TagsTrimCheck
import com.hanggrian.rulebook.checkstyle.checks.UnnecessaryContinueCheck
import com.hanggrian.rulebook.checkstyle.checks.UnnecessaryLeadingBlankLineCheck
import com.hanggrian.rulebook.checkstyle.checks.UnnecessaryReturnCheck
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport
import com.puppycrawl.tools.checkstyle.DefaultConfiguration
import com.puppycrawl.tools.checkstyle.TreeWalker
import java.nio.charset.StandardCharsets.UTF_8

open class AllChecksTest :
    AbstractModuleTestSupport(),
    JvmBasedTest {
    protected val checker by lazy {
        createChecker(
            DefaultConfiguration("root").apply {
                addProperty("charset", UTF_8.name())
                addConfiguration<DuplicateBlankLineCheck>()
                addConfiguration<UnnecessaryLeadingBlankLineCheck>()
                addChild(
                    DefaultConfiguration(TreeWalker::class.simpleName).apply {
                        addConfiguration<AbstractFunctionInAbstractClassCheck>()
                        addConfiguration<AssignmentWrapCheck>()
                        addConfiguration<BlockCommentTrimCheck>()
                        addConfiguration<BlockTagPunctuationCheck>()
                        addConfiguration<BracesTrimCheck>()
                        addConfiguration<CaseSeparatorCheck>()
                        addConfiguration<ChainCallWrapCheck>()
                        addConfiguration<CommentSpacesCheck>()
                        addConfiguration<CommentTrimCheck>()
                        addConfiguration<CommonFunctionPositionCheck>()
                        addConfiguration<ComplicatedAssignmentCheck>()
                        addConfiguration<DuplicateBlankLineInBlockCommentCheck>()
                        addConfiguration<DuplicateBlankLineInCommentCheck>()
                        addConfiguration<ExceptionInheritanceCheck>()
                        addConfiguration<LambdaWrapCheck>()
                        addConfiguration<LonelyCaseCheck>()
                        addConfiguration<LonelyIfCheck>()
                        addConfiguration<LowercaseDCheck>()
                        addConfiguration<LowercaseFCheck>()
                        addConfiguration<LowercaseHexCheck>()
                        addConfiguration<MeaninglessWordCheck>()
                        addConfiguration<MemberOrderCheck>()
                        addConfiguration<NestedIfElseCheck>()
                        addConfiguration<NoParenthesesInLambdaCheck>()
                        addConfiguration<NoRedundantQualifierCheck>()
                        addConfiguration<ParameterWrapCheck>()
                        addConfiguration<ParenthesesTrimCheck>()
                        addConfiguration<RedundantDefaultCheck>()
                        addConfiguration<RedundantElseCheck>()
                        addConfiguration<SimpleSizeComparisonCheck>()
                        addConfiguration<TagsTrimCheck>()
                        addConfiguration<UnnecessaryContinueCheck>()
                        addConfiguration<UnnecessaryReturnCheck>()
                    },
                )
            },
        )!!
    }

    final override fun getPackageLocation(): String = "com/hanggrian/rulebook/checkstyle"

    final override fun getCode(file: String): String = getPath(file)

    private inline fun <reified T> DefaultConfiguration.addConfiguration() =
        addChild(DefaultConfiguration(T::class.java.canonicalName))
}
