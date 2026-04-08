package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.JvmBasedTest
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
import com.hanggrian.rulebook.checkstyle.checks.ComplicatedSizeEqualityCheck
import com.hanggrian.rulebook.checkstyle.checks.DuplicateBlankLineCheck
import com.hanggrian.rulebook.checkstyle.checks.DuplicateBlankLineInBlockCommentCheck
import com.hanggrian.rulebook.checkstyle.checks.DuplicateBlankLineInCommentCheck
import com.hanggrian.rulebook.checkstyle.checks.InternalErrorCheck
import com.hanggrian.rulebook.checkstyle.checks.LambdaWrapCheck
import com.hanggrian.rulebook.checkstyle.checks.LonelyCaseCheck
import com.hanggrian.rulebook.checkstyle.checks.LonelyIfCheck
import com.hanggrian.rulebook.checkstyle.checks.LowercaseDCheck
import com.hanggrian.rulebook.checkstyle.checks.LowercaseFCheck
import com.hanggrian.rulebook.checkstyle.checks.LowercaseHexadecimalCheck
import com.hanggrian.rulebook.checkstyle.checks.MeaninglessWordCheck
import com.hanggrian.rulebook.checkstyle.checks.MemberOrderCheck
import com.hanggrian.rulebook.checkstyle.checks.NestedIfElseCheck
import com.hanggrian.rulebook.checkstyle.checks.ParameterWrapCheck
import com.hanggrian.rulebook.checkstyle.checks.ParenthesesTrimCheck
import com.hanggrian.rulebook.checkstyle.checks.RedundantDefaultCheck
import com.hanggrian.rulebook.checkstyle.checks.RedundantElseCheck
import com.hanggrian.rulebook.checkstyle.checks.RedundantQualifierCheck
import com.hanggrian.rulebook.checkstyle.checks.TagsTrimCheck
import com.hanggrian.rulebook.checkstyle.checks.UnnecessaryAbstractCheck
import com.hanggrian.rulebook.checkstyle.checks.UnnecessaryContinueCheck
import com.hanggrian.rulebook.checkstyle.checks.UnnecessaryInitialBlankLineCheck
import com.hanggrian.rulebook.checkstyle.checks.UnnecessaryParenthesesInLambdaCheck
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
                addConfiguration<UnnecessaryInitialBlankLineCheck>()
                addChild(
                    DefaultConfiguration(TreeWalker::class.simpleName).apply {
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
                        addConfiguration<ComplicatedSizeEqualityCheck>()
                        addConfiguration<DuplicateBlankLineInBlockCommentCheck>()
                        addConfiguration<DuplicateBlankLineInCommentCheck>()
                        addConfiguration<InternalErrorCheck>()
                        addConfiguration<LambdaWrapCheck>()
                        addConfiguration<LonelyCaseCheck>()
                        addConfiguration<LonelyIfCheck>()
                        addConfiguration<LowercaseDCheck>()
                        addConfiguration<LowercaseFCheck>()
                        addConfiguration<LowercaseHexadecimalCheck>()
                        addConfiguration<MeaninglessWordCheck>()
                        addConfiguration<MemberOrderCheck>()
                        addConfiguration<NestedIfElseCheck>()
                        addConfiguration<ParameterWrapCheck>()
                        addConfiguration<ParenthesesTrimCheck>()
                        addConfiguration<RedundantDefaultCheck>()
                        addConfiguration<RedundantElseCheck>()
                        addConfiguration<RedundantQualifierCheck>()
                        addConfiguration<TagsTrimCheck>()
                        addConfiguration<UnnecessaryAbstractCheck>()
                        addConfiguration<UnnecessaryContinueCheck>()
                        addConfiguration<UnnecessaryParenthesesInLambdaCheck>()
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
