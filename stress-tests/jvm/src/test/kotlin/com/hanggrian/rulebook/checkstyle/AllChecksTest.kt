package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.JvmBasedTest
import com.hanggrian.rulebook.checkstyle.checks.AbstractFunctionInAbstractClassCheck
import com.hanggrian.rulebook.checkstyle.checks.AssignmentWrappingCheck
import com.hanggrian.rulebook.checkstyle.checks.BlockCommentTrimmingCheck
import com.hanggrian.rulebook.checkstyle.checks.BlockTagPunctuationCheck
import com.hanggrian.rulebook.checkstyle.checks.BracesTrimmingCheck
import com.hanggrian.rulebook.checkstyle.checks.CaseSeparatorCheck
import com.hanggrian.rulebook.checkstyle.checks.ChainCallWrappingCheck
import com.hanggrian.rulebook.checkstyle.checks.CommentSpacingCheck
import com.hanggrian.rulebook.checkstyle.checks.CommentTrimmingCheck
import com.hanggrian.rulebook.checkstyle.checks.CommonFunctionPositionCheck
import com.hanggrian.rulebook.checkstyle.checks.ExceptionInheritanceCheck
import com.hanggrian.rulebook.checkstyle.checks.LambdaWrappingCheck
import com.hanggrian.rulebook.checkstyle.checks.LowercaseDCheck
import com.hanggrian.rulebook.checkstyle.checks.LowercaseFCheck
import com.hanggrian.rulebook.checkstyle.checks.LowercaseHexCheck
import com.hanggrian.rulebook.checkstyle.checks.MemberOrderCheck
import com.hanggrian.rulebook.checkstyle.checks.NoDuplicateBlankLineCheck
import com.hanggrian.rulebook.checkstyle.checks.NoDuplicateBlankLineInBlockCommentCheck
import com.hanggrian.rulebook.checkstyle.checks.NoDuplicateBlankLineInCommentCheck
import com.hanggrian.rulebook.checkstyle.checks.NoLeadingBlankLineCheck
import com.hanggrian.rulebook.checkstyle.checks.NoLonelyCaseCheck
import com.hanggrian.rulebook.checkstyle.checks.NoLonelyIfCheck
import com.hanggrian.rulebook.checkstyle.checks.NoMeaninglessWordCheck
import com.hanggrian.rulebook.checkstyle.checks.NoNestedIfElseCheck
import com.hanggrian.rulebook.checkstyle.checks.NoParenthesesInLambdaCheck
import com.hanggrian.rulebook.checkstyle.checks.NoRedundantDefaultCheck
import com.hanggrian.rulebook.checkstyle.checks.NoRedundantElseCheck
import com.hanggrian.rulebook.checkstyle.checks.NoRedundantQualifierCheck
import com.hanggrian.rulebook.checkstyle.checks.NoUselessContinueCheck
import com.hanggrian.rulebook.checkstyle.checks.NoUselessReturnCheck
import com.hanggrian.rulebook.checkstyle.checks.ParameterWrappingCheck
import com.hanggrian.rulebook.checkstyle.checks.ParenthesesTrimmingCheck
import com.hanggrian.rulebook.checkstyle.checks.ShorthandAssignmentCheck
import com.hanggrian.rulebook.checkstyle.checks.SimpleSizeComparisonCheck
import com.hanggrian.rulebook.checkstyle.checks.TagsTrimmingCheck
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
                addConfiguration<NoDuplicateBlankLineCheck>()
                addConfiguration<NoLeadingBlankLineCheck>()
                addChild(
                    DefaultConfiguration(TreeWalker::class.simpleName).apply {
                        addConfiguration<AbstractFunctionInAbstractClassCheck>()
                        addConfiguration<AssignmentWrappingCheck>()
                        addConfiguration<BlockCommentTrimmingCheck>()
                        addConfiguration<BlockTagPunctuationCheck>()
                        addConfiguration<BracesTrimmingCheck>()
                        addConfiguration<CaseSeparatorCheck>()
                        addConfiguration<ChainCallWrappingCheck>()
                        addConfiguration<CommentSpacingCheck>()
                        addConfiguration<CommentTrimmingCheck>()
                        addConfiguration<CommonFunctionPositionCheck>()
                        addConfiguration<ExceptionInheritanceCheck>()
                        addConfiguration<LambdaWrappingCheck>()
                        addConfiguration<LowercaseDCheck>()
                        addConfiguration<LowercaseFCheck>()
                        addConfiguration<LowercaseHexCheck>()
                        addConfiguration<MemberOrderCheck>()
                        addConfiguration<NoDuplicateBlankLineInBlockCommentCheck>()
                        addConfiguration<NoDuplicateBlankLineInCommentCheck>()
                        addConfiguration<NoLonelyCaseCheck>()
                        addConfiguration<NoLonelyIfCheck>()
                        addConfiguration<NoMeaninglessWordCheck>()
                        addConfiguration<NoNestedIfElseCheck>()
                        addConfiguration<NoParenthesesInLambdaCheck>()
                        addConfiguration<NoRedundantDefaultCheck>()
                        addConfiguration<NoRedundantElseCheck>()
                        addConfiguration<NoRedundantQualifierCheck>()
                        addConfiguration<NoUselessContinueCheck>()
                        addConfiguration<NoUselessReturnCheck>()
                        addConfiguration<ParameterWrappingCheck>()
                        addConfiguration<ParenthesesTrimmingCheck>()
                        addConfiguration<ShorthandAssignmentCheck>()
                        addConfiguration<SimpleSizeComparisonCheck>()
                        addConfiguration<TagsTrimmingCheck>()
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
