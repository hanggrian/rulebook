package com.hanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport
import com.puppycrawl.tools.checkstyle.DefaultConfiguration
import com.puppycrawl.tools.checkstyle.TreeWalker
import java.nio.charset.StandardCharsets
import kotlin.test.Test

class AllChecksTest : AbstractModuleTestSupport() {
    private val checker =
        createChecker(
            DefaultConfiguration("root").apply {
                addProperty("charset", StandardCharsets.UTF_8.name())
                addConfiguration<DuplicateBlankLineCheck>()
                addChild(
                    DefaultConfiguration(TreeWalker::class.simpleName).apply {
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

    override fun getPackageLocation(): String = "AllChecks"

    @Test
    fun com_google_common_truth_StringSubject(): Unit =
        verify(
            checker,
            getPath("StringSubject.java"),
            "129:107: Do not join parentheses with parameters.",
            "134:91: Do not join parentheses with parameters.",
            "154:50: Do not join parentheses with parameters.",
            "154:51: Do not join parentheses with parameters.",
            "159:91: Do not join parentheses with parameters.",
            "217:9: Invert if condition.",
            "221:95: Do not join parentheses with parameters.",
            "268:56: Do not join parentheses with parameters.",
            "270:15: Lift else and add return in if block.",
            "273:74: Break each parameter into newline.",
            "273:88: Break each parameter into newline.",
            "273:109: Do not join parentheses with parameters.",
            "289:56: Do not join parentheses with parameters.",
            "291:15: Lift else and add return in if block.",
            "294:71: Break each parameter into newline.",
            "294:85: Break each parameter into newline.",
            "294:106: Do not join parentheses with parameters.",
            "307:52: Do not join parentheses with parameters.",
            "310:66: Break each parameter into newline.",
            "310:80: Break each parameter into newline.",
            "310:101: Do not join parentheses with parameters.",
            "322:52: Do not join parentheses with parameters.",
            "325:70: Break each parameter into newline.",
            "325:84: Break each parameter into newline.",
            "325:105: Do not join parentheses with parameters.",
            "344:47: Do not join parentheses with parameters.",
        )

    @Test
    fun com_puppycrawl_tools_checkstyle_Checker(): Unit =
        verify(
            checker,
            getPath("Checker.java"),
            "64: Remove blank line after {.",
            "75:54: Break assignment into newline.",
            "76:28: Do not join parentheses with parameters.",
            "183:9: Invert if condition.",
            "223:40: Break assignment into newline.",
            "223:45: Put newline before ..",
            "248:22: Put newline before ..",
            "252:52: Put newline before ..",
            "254:13: Omit newline before ..",
            "312:85: Break each parameter into newline.",
            "312:87: Do not join parentheses with parameters.",
            "345:29: Do not join parentheses with parameters.",
            "345:30: Do not join parentheses with parameters.",
            "346:48: Break each parameter into newline.",
            "347:50: Break each parameter into newline.",
            "347:64: Break each parameter into newline.",
            "347:68: Break each parameter into newline.",
            "347:72: Do not join parentheses with parameters.",
            "347:73: Do not join parentheses with parameters.",
            "362:29: Do not join parentheses with parameters.",
            "362:30: Do not join parentheses with parameters.",
            "363:48: Break each parameter into newline.",
            "365:31: Break each parameter into newline.",
            "365:35: Break each parameter into newline.",
            "365:39: Do not join parentheses with parameters.",
            "365:40: Do not join parentheses with parameters.",
            "409:13: Invert if condition.",
            "446:46: Break assignment into newline.",
            "448:29: Do not join parentheses with parameters.",
            "449:34: Do not join parentheses with parameters.",
            "476:13: Invert if condition.",
            "484:89: Break each parameter into newline.",
            "484:91: Do not join parentheses with parameters.",
            "505:74: Do not join parentheses with parameters.",
            "551:9: Invert if condition.",
            "618:67: Do not join parentheses with parameters.",
            "658:55: Break assignment into newline.",
            "659:52: Break each parameter into newline.",
            "660:25: Break each parameter into newline.",
            "660:29: Do not join parentheses with parameters.",
            "664: Remove blank line before }.",
        )

    @Test
    fun com_squareup_javapoet_JavaFile(): Unit =
        verify(
            checker,
            getPath("JavaFile.java"),
            "47:59: Break assignment into newline.",
            "89:43: Break assignment into newline.",
            "131:22: Do not join parentheses with parameters.",
            "132:55: Break each parameter into newline.",
            "132:64: Do not join parentheses with parameters.",
            "165:27: Break assignment into newline.",
            "169:42: Break assignment into newline.",
            "169:64: Do not join parentheses with parameters.",
            "170:81: Do not join parentheses with parameters.",
            "222:5: Move 'equals' to last.",
            "229:5: Move 'hashCode' to last.",
            "233:5: Move 'toString' to last.",
            "244:19: Break assignment into newline.",
            "244:29: Do not join parentheses with parameters.",
            "247:36: Do not join parentheses with parameters.",
        )

    @Test
    fun retrofit2_RequestBuilder(): Unit =
        verify(
            checker,
            getPath("RequestBuilder.java"),
            "75:28: Do not join parentheses with parameters.",
            "130:92: Do not join parentheses with parameters.",
            "156:21: Break each parameter into newline.",
            "156:35: Break each parameter into newline.",
            "156:44: Break each parameter into newline.",
            "156:55: Break each parameter into newline.",
            "156:77: Do not join parentheses with parameters.",
            "192:85: Do not join parentheses with parameters.",
            "200:11: Lift else and add return in if block.",
            "244:85: Do not join parentheses with parameters.",
        )

    private inline fun <reified T> DefaultConfiguration.addConfiguration() =
        addChild(DefaultConfiguration(T::class.java.canonicalName))
}
