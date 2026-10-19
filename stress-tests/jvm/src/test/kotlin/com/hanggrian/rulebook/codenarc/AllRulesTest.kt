package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.JvmBasedTest
import com.hanggrian.rulebook.codenarc.rules.AssignmentWrappingRule
import com.hanggrian.rulebook.codenarc.rules.BlankLineBeforeBlockTagsRule
import com.hanggrian.rulebook.codenarc.rules.BlockCommentClippingRule
import com.hanggrian.rulebook.codenarc.rules.BlockTagIndentationRule
import com.hanggrian.rulebook.codenarc.rules.BlockTagOrderRule
import com.hanggrian.rulebook.codenarc.rules.BlockTagPunctuationRule
import com.hanggrian.rulebook.codenarc.rules.BracesClippingRule
import com.hanggrian.rulebook.codenarc.rules.BracesSpacingRule
import com.hanggrian.rulebook.codenarc.rules.BracketsClippingRule
import com.hanggrian.rulebook.codenarc.rules.BracketsTrimmingRule
import com.hanggrian.rulebook.codenarc.rules.CaseSeparatorRule
import com.hanggrian.rulebook.codenarc.rules.ChainCallWrappingRule
import com.hanggrian.rulebook.codenarc.rules.CommentTrimmingRule
import com.hanggrian.rulebook.codenarc.rules.CommonFunctionPositionRule
import com.hanggrian.rulebook.codenarc.rules.GenericNameRule
import com.hanggrian.rulebook.codenarc.rules.IllegalVariableNameRule
import com.hanggrian.rulebook.codenarc.rules.ImportOrderRule
import com.hanggrian.rulebook.codenarc.rules.InnerClassPositionRule
import com.hanggrian.rulebook.codenarc.rules.LambdaWrappingRule
import com.hanggrian.rulebook.codenarc.rules.LowercaseDRule
import com.hanggrian.rulebook.codenarc.rules.LowercaseFRule
import com.hanggrian.rulebook.codenarc.rules.LowercaseHexRule
import com.hanggrian.rulebook.codenarc.rules.LowercaseIRule
import com.hanggrian.rulebook.codenarc.rules.MemberOrderRule
import com.hanggrian.rulebook.codenarc.rules.MemberSeparatorRule
import com.hanggrian.rulebook.codenarc.rules.ModifierOrderRule
import com.hanggrian.rulebook.codenarc.rules.NoDuplicateBlankLineInCommentRule
import com.hanggrian.rulebook.codenarc.rules.NoDuplicateSpaceRule
import com.hanggrian.rulebook.codenarc.rules.NoEmptyFileRule
import com.hanggrian.rulebook.codenarc.rules.NoLonelyCaseRule
import com.hanggrian.rulebook.codenarc.rules.NoLonelyIfRule
import com.hanggrian.rulebook.codenarc.rules.NoMeaninglessWordRule
import com.hanggrian.rulebook.codenarc.rules.NoNestedIfElseRule
import com.hanggrian.rulebook.codenarc.rules.NoParenthesesInLambdaRule
import com.hanggrian.rulebook.codenarc.rules.NoPublicConstructorInUtilityClassRule
import com.hanggrian.rulebook.codenarc.rules.NoRedundantDefaultRule
import com.hanggrian.rulebook.codenarc.rules.NoRedundantElseRule
import com.hanggrian.rulebook.codenarc.rules.NoUselessContinueRule
import com.hanggrian.rulebook.codenarc.rules.NoUselessReturnRule
import com.hanggrian.rulebook.codenarc.rules.OperatorWrappingRule
import com.hanggrian.rulebook.codenarc.rules.OverloadFunctionPositionRule
import com.hanggrian.rulebook.codenarc.rules.ParameterWrappingRule
import com.hanggrian.rulebook.codenarc.rules.ParenthesesTrimmingRule
import com.hanggrian.rulebook.codenarc.rules.ShorthandAssignmentRule
import com.hanggrian.rulebook.codenarc.rules.SimpleSizeComparisonRule
import com.hanggrian.rulebook.codenarc.rules.StatementWrappingRule
import com.hanggrian.rulebook.codenarc.rules.TagsClippingRule
import com.hanggrian.rulebook.codenarc.rules.TagsTrimmingRule
import com.hanggrian.rulebook.codenarc.rules.TodoCommentStyleRule
import com.hanggrian.rulebook.codenarc.rules.TrailingCommaInCallRule
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
            AssignmentWrappingRule(),
            BlankLineBeforeBlockTagsRule(),
            BlockCommentClippingRule(),
            BlockTagIndentationRule(),
            BlockTagOrderRule(),
            BlockTagPunctuationRule(),
            BracesClippingRule(),
            BracesSpacingRule(),
            BracketsClippingRule(),
            BracketsTrimmingRule(),
            CaseSeparatorRule(),
            ChainCallWrappingRule(),
            CommentTrimmingRule(),
            CommonFunctionPositionRule(),
            GenericNameRule(),
            IllegalVariableNameRule(),
            ImportOrderRule(),
            InnerClassPositionRule(),
            LambdaWrappingRule(),
            LowercaseDRule(),
            LowercaseFRule(),
            LowercaseHexRule(),
            LowercaseIRule(),
            MemberOrderRule(),
            MemberSeparatorRule(),
            ModifierOrderRule(),
            NoDuplicateBlankLineInCommentRule(),
            NoDuplicateSpaceRule(),
            NoEmptyFileRule(),
            NoLonelyCaseRule(),
            NoLonelyIfRule(),
            NoMeaninglessWordRule(),
            NoNestedIfElseRule(),
            NoParenthesesInLambdaRule(),
            NoPublicConstructorInUtilityClassRule(),
            NoRedundantDefaultRule(),
            NoRedundantElseRule(),
            NoUselessContinueRule(),
            NoUselessReturnRule(),
            OperatorWrappingRule(),
            OverloadFunctionPositionRule(),
            ParameterWrappingRule(),
            ParenthesesTrimmingRule(),
            ShorthandAssignmentRule(),
            SimpleSizeComparisonRule(),
            StatementWrappingRule(),
            TagsClippingRule(),
            TagsTrimmingRule(),
            TodoCommentStyleRule(),
            TrailingCommaInCallRule(),
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
