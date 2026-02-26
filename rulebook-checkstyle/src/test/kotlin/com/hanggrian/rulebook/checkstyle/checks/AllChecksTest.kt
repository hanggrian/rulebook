package com.hanggrian.rulebook.checkstyle.checks

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport
import com.puppycrawl.tools.checkstyle.DefaultConfiguration
import com.puppycrawl.tools.checkstyle.TreeWalker
import java.nio.charset.StandardCharsets.UTF_8
import kotlin.test.Test

class AllChecksTest : AbstractModuleTestSupport() {
    private val checker =
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
                        addConfiguration<CommentSpaceCheck>()
                        addConfiguration<CommentTrimCheck>()
                        addConfiguration<CommonFunctionPositionCheck>()
                        addConfiguration<DuplicateBlankLineInBlockCommentCheck>()
                        addConfiguration<DuplicateBlankLineInCommentCheck>()
                        addConfiguration<InternalErrorCheck>()
                        addConfiguration<LambdaWrapCheck>()
                        addConfiguration<LonelyCaseCheck>()
                        addConfiguration<LonelyIfCheck>()
                        addConfiguration<LowercaseDCheck>()
                        addConfiguration<LowercaseFCheck>()
                        addConfiguration<LowercaseHexCheck>()
                        addConfiguration<MeaninglessWordCheck>()
                        addConfiguration<MemberOrderCheck>()
                        addConfiguration<NestedIfElseCheck>()
                        addConfiguration<ParameterWrapCheck>()
                        addConfiguration<ParenthesesTrimCheck>()
                        addConfiguration<RedundantDefaultCheck>()
                        addConfiguration<RedundantElseCheck>()
                        addConfiguration<RedundantIfCheck>()
                        addConfiguration<RedundantQualifierCheck>()
                        addConfiguration<TagsTrimCheck>()
                        addConfiguration<UnnecessaryAbstractCheck>()
                        addConfiguration<UnnecessaryContinueCheck>()
                        addConfiguration<UnnecessaryParenthesesInLambdaCheck>()
                        addConfiguration<UnnecessaryReturnCheck>()
                    },
                )
            },
        )

    override fun getPackageLocation(): String = "AllChecks"

    @Test
    fun com_puppycrawl_tools_checkstyle_Checker(): Unit =
        verify(
            checker,
            getPath("Checker.java"),
            "64: Remove blank line after '{'.",
            "72:5: Arrange member 'property' before 'static member'.",
            "75:54: Break assignment into newline.",
            "183:9: Invert 'if' condition.",
            "196:24: End '@param' with a period.",
            "205:23: End '@param' with a period.",
            "223:40: Break assignment into newline.",
            "223:45: Put newline before '.'.",
            "248:22: Put newline before '.'.",
            "254:13: Omit newline before '.'.",
            "312:85: Break each parameter into newline.",
            "346:48: Break each parameter into newline.",
            "347:50: Break each parameter into newline.",
            "347:64: Break each parameter into newline.",
            "347:68: Break each parameter into newline.",
            "363:48: Break each parameter into newline.",
            "365:31: Break each parameter into newline.",
            "365:35: Break each parameter into newline.",
            "374:7: End '@param' with a period.",
            "386:7: End '@param' with a period.",
            "400:24: End '@param' with a period.",
            "401:22: End '@param' with a period.",
            "409:13: Invert 'if' condition.",
            "425:7: End '@param' with a period.",
            "446:46: Break assignment into newline.",
            "484:89: Break each parameter into newline.",
            "513:28: End '@param' with a period.",
            "523:22: End '@param' with a period.",
            "532:22: End '@param' with a period.",
            "551:9: Invert 'if' condition.",
            "568:29: End '@param' with a period.",
            "577:29: End '@param' with a period.",
            "586:30: End '@param' with a period.",
            "596:25: End '@param' with a period.",
            "611:23: End '@param' with a period.",
            "635:39: End '@param' with a period.",
            "655:16: End '@return' with a period.",
            "658:55: Break assignment into newline.",
            "659:52: Break each parameter into newline.",
            "660:25: Break each parameter into newline.",
            "664: Remove blank line before '}'.",
        )

    @Test
    fun com_squareup_javapoet_JavaFile(): Unit =
        verify(
            checker,
            getPath("JavaFile.java"),
            "47:59: Break assignment into newline.",
            "59:5: Arrange member 'property' before 'static member'.",
            "89:43: Break assignment into newline.",
            "132:55: Break each parameter into newline.",
            "165:27: Break assignment into newline.",
            "169:42: Break assignment into newline.",
            "222:5: Move 'equals' to last.",
            "229:5: Move 'hashCode' to last.",
            "233:5: Move 'toString' to last.",
            "244:19: Break assignment into newline.",
            "268:5: Arrange member 'function' before 'static member'.",
        )

    @Test
    fun retrofit2_RequestBuilder(): Unit =
        verify(
            checker,
            getPath("RequestBuilder.java"),
            "52:5: Arrange member 'property' before 'static member'.",
            "156:21: Break each parameter into newline.",
            "156:35: Break each parameter into newline.",
            "156:44: Break each parameter into newline.",
            "156:55: Break each parameter into newline.",
            "186:5: Arrange member 'function' before 'static member'.",
            "200:11: Lift 'else' and add 'return' in 'if' block.",
        )

    @Test
    fun com_google_common_truth_StringSubject(): Unit =
        verify(
            checker,
            getPath("StringSubject.java"),
            "217:9: Invert 'if' condition.",
            "270:15: Lift 'else' and add 'return' in 'if' block.",
            "271:17: Replace 'else' with 'else if' condition.",
            "273:74: Break each parameter into newline.",
            "273:88: Break each parameter into newline.",
            "291:15: Lift 'else' and add 'return' in 'if' block.",
            "292:17: Replace 'else' with 'else if' condition.",
            "294:71: Break each parameter into newline.",
            "294:85: Break each parameter into newline.",
            "310:66: Break each parameter into newline.",
            "310:80: Break each parameter into newline.",
            "325:70: Break each parameter into newline.",
            "325:84: Break each parameter into newline.",
        )

    private inline fun <reified T> DefaultConfiguration.addConfiguration() =
        addChild(DefaultConfiguration(T::class.java.canonicalName))
}
