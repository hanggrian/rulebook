package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.JvmBasedTest
import com.hanggrian.rulebook.codenarc.rules.AssignmentWrapRule
import com.hanggrian.rulebook.codenarc.rules.BlockCommentClipRule
import com.hanggrian.rulebook.codenarc.rules.BlockTagIndentationRule
import com.hanggrian.rulebook.codenarc.rules.BlockTagOrderRule
import com.hanggrian.rulebook.codenarc.rules.BlockTagPunctuationRule
import com.hanggrian.rulebook.codenarc.rules.BracesClipRule
import com.hanggrian.rulebook.codenarc.rules.BracesSpacesRule
import com.hanggrian.rulebook.codenarc.rules.BracketsClipRule
import com.hanggrian.rulebook.codenarc.rules.BracketsTrimRule
import com.hanggrian.rulebook.codenarc.rules.CaseSeparatorRule
import com.hanggrian.rulebook.codenarc.rules.ChainCallWrapRule
import com.hanggrian.rulebook.codenarc.rules.CommentTrimRule
import com.hanggrian.rulebook.codenarc.rules.CommonFunctionPositionRule
import com.hanggrian.rulebook.codenarc.rules.ComplicatedAssignmentRule
import com.hanggrian.rulebook.codenarc.rules.ComplicatedSizeEqualityRule
import com.hanggrian.rulebook.codenarc.rules.DuplicateBlankLineInCommentRule
import com.hanggrian.rulebook.codenarc.rules.DuplicateSpaceRule
import com.hanggrian.rulebook.codenarc.rules.EmptyFileRule
import com.hanggrian.rulebook.codenarc.rules.GenericNameRule
import com.hanggrian.rulebook.codenarc.rules.IllegalVariableNameRule
import com.hanggrian.rulebook.codenarc.rules.ImportOrderRule
import com.hanggrian.rulebook.codenarc.rules.InnerClassPositionRule
import com.hanggrian.rulebook.codenarc.rules.LambdaWrapRule
import com.hanggrian.rulebook.codenarc.rules.LonelyCaseRule
import com.hanggrian.rulebook.codenarc.rules.LonelyIfRule
import com.hanggrian.rulebook.codenarc.rules.LowercaseDRule
import com.hanggrian.rulebook.codenarc.rules.LowercaseFRule
import com.hanggrian.rulebook.codenarc.rules.LowercaseHexadecimalRule
import com.hanggrian.rulebook.codenarc.rules.LowercaseIRule
import com.hanggrian.rulebook.codenarc.rules.MeaninglessWordRule
import com.hanggrian.rulebook.codenarc.rules.MemberOrderRule
import com.hanggrian.rulebook.codenarc.rules.MemberSeparatorRule
import com.hanggrian.rulebook.codenarc.rules.MissingBlankLineBeforeBlockTagsRule
import com.hanggrian.rulebook.codenarc.rules.MissingPrivateConstructorRule
import com.hanggrian.rulebook.codenarc.rules.ModifierOrderRule
import com.hanggrian.rulebook.codenarc.rules.NestedIfElseRule
import com.hanggrian.rulebook.codenarc.rules.OperatorWrapRule
import com.hanggrian.rulebook.codenarc.rules.OverloadFunctionPositionRule
import com.hanggrian.rulebook.codenarc.rules.ParameterWrapRule
import com.hanggrian.rulebook.codenarc.rules.ParenthesesTrimRule
import com.hanggrian.rulebook.codenarc.rules.RedundantDefaultRule
import com.hanggrian.rulebook.codenarc.rules.RedundantElseRule
import com.hanggrian.rulebook.codenarc.rules.RedundantEqualityRule
import com.hanggrian.rulebook.codenarc.rules.StatementWrapRule
import com.hanggrian.rulebook.codenarc.rules.TagsClipRule
import com.hanggrian.rulebook.codenarc.rules.TagsTrimRule
import com.hanggrian.rulebook.codenarc.rules.TodoCommentRule
import com.hanggrian.rulebook.codenarc.rules.TrailingCommaInCallRule
import com.hanggrian.rulebook.codenarc.rules.UnnecessaryContinueRule
import com.hanggrian.rulebook.codenarc.rules.UnnecessaryParenthesesInLambdaRule
import com.hanggrian.rulebook.codenarc.rules.UnnecessaryReturnRule
import org.codenarc.analyzer.SuppressionAnalyzer
import org.codenarc.rule.Rule
import org.codenarc.rule.Violation
import org.codenarc.source.AbstractSourceCode
import org.codenarc.source.CustomCompilerPhaseSourceDecorator
import org.codenarc.source.SourceCode
import org.codenarc.source.SourceCode.DEFAULT_COMPILER_PHASE
import org.codenarc.source.SourceString
import org.codenarc.test.AbstractTestCase
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

abstract class AllRulesTest :
    AbstractTestCase(),
    JvmBasedTest {
    protected val rules by lazy {
        setOf<Rule>(
            AssignmentWrapRule(),
            BlockCommentClipRule(),
            BlockTagIndentationRule(),
            BlockTagOrderRule(),
            BlockTagPunctuationRule(),
            BracesClipRule(),
            BracesSpacesRule(),
            BracketsClipRule(),
            BracketsTrimRule(),
            CaseSeparatorRule(),
            ChainCallWrapRule(),
            CommentTrimRule(),
            CommonFunctionPositionRule(),
            ComplicatedAssignmentRule(),
            ComplicatedSizeEqualityRule(),
            DuplicateBlankLineInCommentRule(),
            DuplicateSpaceRule(),
            EmptyFileRule(),
            GenericNameRule(),
            IllegalVariableNameRule(),
            ImportOrderRule(),
            InnerClassPositionRule(),
            LambdaWrapRule(),
            LonelyCaseRule(),
            LonelyIfRule(),
            LowercaseDRule(),
            LowercaseFRule(),
            LowercaseHexadecimalRule(),
            LowercaseIRule(),
            MeaninglessWordRule(),
            MemberOrderRule(),
            MemberSeparatorRule(),
            MissingBlankLineBeforeBlockTagsRule(),
            MissingPrivateConstructorRule(),
            ModifierOrderRule(),
            NestedIfElseRule(),
            OperatorWrapRule(),
            OverloadFunctionPositionRule(),
            ParameterWrapRule(),
            ParenthesesTrimRule(),
            RedundantDefaultRule(),
            RedundantElseRule(),
            RedundantEqualityRule(),
            StatementWrapRule(),
            TagsClipRule(),
            TagsTrimRule(),
            TodoCommentRule(),
            TrailingCommaInCallRule(),
            UnnecessaryContinueRule(),
            UnnecessaryParenthesesInLambdaRule(),
            UnnecessaryReturnRule(),
        )
    }
    protected val sourceCodeName by lazy { "AllRules.groovy" }
    protected val sourceCodePath by lazy { "com/hanggrian/rulebook/codenarc" }

    protected fun violationOf(line: Int, source: String, message: String) =
        mapOf(
            "line" to line,
            "source" to source,
            "message" to message,
        )

    /**
     * @see org.codenarc.rule.AbstractRuleTestCase.assertViolations
     */
    protected fun assertViolations(source: String, vararg violationMaps: Map<String, Any>) {
        val rawViolations = applyRuleTo(source)
        rawViolations.sortBy { it.lineNumber }
        assertEquals(
            rawViolations.size,
            violationMaps.size,
            "Expected ${violationMaps.size} violations\n" +
                "Found ${rawViolations.size}: \n" +
                "    ${rawViolations.joinToString("\n    ")}\n",
        )
        val validKeys =
            listOf("line", "lineNumber", "source", "sourceLineText", "message", "messageText")
        violationMaps.forEachIndexed { index, violationMap ->
            assertTrue(
                violationMap
                    .keys
                    .all { it in validKeys },
                "violationMap keys must be one of $validKeys",
            )
            assertViolation(
                rawViolations[index],
                violationMap["line"]?.toString()?.toInt()
                    ?: violationMap["lineNumber"]!!.toString().toInt(),
                violationMap["source"]?.toString()
                    ?: violationMap["sourceLineText"]!!.toString(),
                violationMap["message"]
                    ?: violationMap["messageText"],
            )
        }
    }

    /**
     * @see org.codenarc.rule.AbstractRuleTestCase.assertViolation
     */
    protected fun assertViolation(
        violation: Violation,
        lineNumber: Int,
        sourceLineText: String,
        messageText: Any? = null,
    ) {
        assertTrue(violation.rule in rules)
        assertEquals(
            violation.lineNumber,
            lineNumber,
            "Wrong line number for violation: \n" +
                "$violation\n" +
                "Expected: $lineNumber\n" +
                "Found:    $violation.lineNumber\n",
        )
        if (sourceLineText.isNotBlank()) {
            assertNotNull(violation.sourceLine)
            assertTrue(
                sourceLineText in violation.sourceLine,
                """
                Problem with source text:
                expected to contain:  $sourceLineText
                actual:               $violation.sourceLine
                """.trimIndent(),
            )
        }
        if (messageText == null) {
            return
        }
        assertNotNull(violation.message, "The violation message was null")
        if (messageText is Collection<*>) {
            messageText.forEach {
                assertTrue(
                    it.toString() in violation.message,
                    "text does not contain [$it]",
                )
            }
            return
        }
        assertTrue(
            messageText.toString() in violation.message,
            "Violation on line $lineNumber\n" +
                "Expected message text: [$messageText]\n" +
                "Found message text:    [$violation.message]\n",
        )
    }

    /**
     * @see org.codenarc.rule.AbstractRuleTestCase.applyRuleTo
     */
    private fun applyRuleTo(source: String): MutableList<Violation> {
        val sourceCode = prepareSourceCode(source)
        assertTrue(sourceCode.isValid)
        val violations = rules.flatMap { it.applyTo(sourceCode) }.toMutableList()
        removeSuppressedViolations(sourceCode, violations)
        log("violations=$violations")
        return violations
    }

    /**
     * @see org.codenarc.rule.AbstractRuleTestCase.removeSuppressedViolations
     */
    private fun removeSuppressedViolations(
        sourceCode: SourceCode,
        violations: MutableList<Violation>,
    ) {
        val suppressionAnalyzer = SuppressionAnalyzer(sourceCode)
        violations.removeAll { suppressionAnalyzer.isViolationSuppressed(it) }
    }

    /**
     * @see org.codenarc.rule.AbstractRuleTestCase.prepareSourceCode
     */
    private fun prepareSourceCode(source: String): SourceCode {
        var sourceCode: AbstractSourceCode = SourceString(source, sourceCodePath, sourceCodeName)
        rules
            .firstOrNull { it.compilerPhase != DEFAULT_COMPILER_PHASE }
            ?.compilerPhase
            ?.run { sourceCode = CustomCompilerPhaseSourceDecorator(sourceCode, this) }
        return sourceCode
    }
}
