package com.hanggrian.rulebook.checkstyle.all

import com.hanggrian.rulebook.checkstyle.AbstractClassDefinitionCheck
import com.hanggrian.rulebook.checkstyle.AssignmentWrapCheck
import com.hanggrian.rulebook.checkstyle.BlockCommentTrimCheck
import com.hanggrian.rulebook.checkstyle.BuiltInFunctionPositionCheck
import com.hanggrian.rulebook.checkstyle.CaseSeparatorCheck
import com.hanggrian.rulebook.checkstyle.ChainCallWrapCheck
import com.hanggrian.rulebook.checkstyle.CodeBlockTrimCheck
import com.hanggrian.rulebook.checkstyle.CommentSpacesCheck
import com.hanggrian.rulebook.checkstyle.DuplicateBlankLineCheck
import com.hanggrian.rulebook.checkstyle.DuplicateBlankLineInBlockCommentCheck
import com.hanggrian.rulebook.checkstyle.DuplicateBlankLineInCommentCheck
import com.hanggrian.rulebook.checkstyle.ExceptionInheritanceCheck
import com.hanggrian.rulebook.checkstyle.IllegalClassFinalNameCheck
import com.hanggrian.rulebook.checkstyle.LambdaWrapCheck
import com.hanggrian.rulebook.checkstyle.MemberOrderCheck
import com.hanggrian.rulebook.checkstyle.NestedIfElseCheck
import com.hanggrian.rulebook.checkstyle.NumberSuffixForDoubleCheck
import com.hanggrian.rulebook.checkstyle.NumberSuffixForFloatCheck
import com.hanggrian.rulebook.checkstyle.ParameterWrapCheck
import com.hanggrian.rulebook.checkstyle.RedundantDefaultCheck
import com.hanggrian.rulebook.checkstyle.RedundantElseCheck
import com.hanggrian.rulebook.checkstyle.RedundantQualifierCheck
import com.hanggrian.rulebook.checkstyle.UnnecessaryBlankLineBeforePackageCheck
import com.hanggrian.rulebook.checkstyle.UnnecessaryParenthesesInLambdaCheck
import com.hanggrian.rulebook.checkstyle.UnnecessarySwitchCheck
import com.hanggrian.rulebook.checkstyle.read
import com.puppycrawl.tools.checkstyle.Checker
import com.puppycrawl.tools.checkstyle.DefaultConfiguration
import kotlin.test.Test
import kotlin.test.assertEquals

class AllChecksTest {
    private val checker =
        Checker().also {
            it.setModuleClassLoader(Thread.currentThread().contextClassLoader)
            it.configure(
                DefaultConfiguration("Checks").apply {
                    addConfiguration<DuplicateBlankLineCheck>()
                    addChild(
                        DefaultConfiguration("TreeWalker").apply {
                            addConfiguration<AbstractClassDefinitionCheck>()
                            addConfiguration<AssignmentWrapCheck>()
                            addConfiguration<BlockCommentTrimCheck>()
                            addConfiguration<BuiltInFunctionPositionCheck>()
                            addConfiguration<CaseSeparatorCheck>()
                            addConfiguration<ChainCallWrapCheck>()
                            addConfiguration<CodeBlockTrimCheck>()
                            addConfiguration<CommentSpacesCheck>()
                            addConfiguration<DuplicateBlankLineInBlockCommentCheck>()
                            addConfiguration<DuplicateBlankLineInCommentCheck>()
                            addConfiguration<ExceptionInheritanceCheck>()
                            addConfiguration<IllegalClassFinalNameCheck>()
                            addConfiguration<LambdaWrapCheck>()
                            addConfiguration<MemberOrderCheck>()
                            addConfiguration<NestedIfElseCheck>()
                            addConfiguration<NumberSuffixForDoubleCheck>()
                            addConfiguration<NumberSuffixForFloatCheck>()
                            addConfiguration<ParameterWrapCheck>()
                            addConfiguration<RedundantDefaultCheck>()
                            addConfiguration<RedundantElseCheck>()
                            addConfiguration<RedundantQualifierCheck>()
                            addConfiguration<UnnecessaryBlankLineBeforePackageCheck>()
                            addConfiguration<UnnecessaryParenthesesInLambdaCheck>()
                            addConfiguration<UnnecessarySwitchCheck>()
                        },
                    )
                },
            )
        }

    @Test
    fun com_puppycrawl_tools_checkstyle_checks_coding_UnnecessaryParenthesesCheck() =
        assertEquals(15, checker.read("all/UnnecessaryParenthesesCheck"))

    private inline fun <reified T> DefaultConfiguration.addConfiguration() =
        addChild(DefaultConfiguration(T::class.java.canonicalName))
}
