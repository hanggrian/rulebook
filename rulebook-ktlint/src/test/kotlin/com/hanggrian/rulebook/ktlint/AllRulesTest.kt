package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.ruleset.standard.rules.AnnotationRule
import com.pinterest.ktlint.test.KtLintAssertThat
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRuleBuilder
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class AllRulesTest {
    private val assertThatCodeAll: (String) -> KtLintAssertThat =
        assertThatRuleBuilder { AnnotationRule() }
            .let {
                var builder = it
                for (rule in AllRules) {
                    builder = builder.addAdditionalRuleProvider { rule.createNewRuleInstance() }
                }
                builder
            }.assertThat()

    @Test
    fun com_pinterest_ktlint_rule_engine_internal_RuleExecutionContext() {
        assertThatCodeAll(
            javaClass
                .getResource("RuleExecutionContext.kt")!!
                .readText(),
        ).hasLintViolationsForAdditionalRule(
            LintViolation(96, 9, "Invert 'if' condition.", false),
            LintViolation(113, 19, "Omit redundant 'else' condition.", false),
            LintViolation(239, 25, "Omit separator ':'.", false),
            LintViolation(241, 25, "Omit separator ':'.", false),
            LintViolation(287, 5, "Invert 'if' condition.", false),
            LintViolation(300, 5, "Extend from class 'Exception'.", false),
        )
    }

    @Test
    fun com_squareup_kotlinpoet_FileSpec() {
        assertThatCodeAll(
            javaClass
                .getResource("FileSpec.kt")!!
                .readText(),
        ).hasLintViolationsForAdditionalRule(
            LintViolation(100, 5, "Move 'writeTo' next to each other.", false),
            LintViolation(115, 5, "Move 'oldWriteTo' next to each other.", false),
            LintViolation(121, 5, "Move 'writeTo' next to each other.", false),
            LintViolation(213, 5, "Move 'equals' to last.", false),
            LintViolation(220, 5, "Move 'hashCode' to last.", false),
            LintViolation(222, 5, "Move 'toString' to last.", false),
            LintViolation(254, 5, "Move inner class to the bottom.", false),
            LintViolation(271, 9, "Arrange member 'property' before 'function'.", false),
            LintViolation(471, 9, "Move 'addCode' next to each other.", false),
            LintViolation(534, 9, "Move 'addAnnotation' next to each other.", false),
            LintViolation(549, 17, "Invert 'if' condition.", false),
        )
    }

    @Test
    fun org_jetbrains_dokka_gradle_DokkaExtension() {
        assertThatCodeAll(
            javaClass
                .getResource("DokkaExtension.kt")!!
                .readText(),
        ).hasLintViolationsForAdditionalRule(
            LintViolation(4, 3, "Convert into single-line.", false),
            LintViolation(288, 5, "Arrange member 'property' before 'function'.", false),
            LintViolation(302, 8, "Add blank line before block tag group.", false),
            LintViolation(308, 6, "Remove blank line after '/**'.", false),
        )
    }

    @Test
    fun org_jetbrains_exposed_dsl_Table() {
        assertThatCodeAll(
            javaClass
                .getResource("Table.kt")!!
                .readText(),
        ).hasLintViolationsForAdditionalRule(
            LintViolation(1, 1, "Reduce file size to '1,000'.", false),
            LintViolation(31, 3, "Convert into single-line.", false),
            LintViolation(41, 7, "Convert into single-line.", false),
            LintViolation(52, 22, "Add blank line after multiline branch.", false),
            LintViolation(62, 3, "Convert into single-line.", false),
            LintViolation(67, 5, "Arrange member 'property' before 'function'.", false),
            LintViolation(212, 3, "Convert into single-line.", false),
            LintViolation(217, 3, "Convert into single-line.", false),
            LintViolation(237, 3, "Convert into single-line.", false),
            LintViolation(257, 5, "Arrange member 'property' before 'function'.", false),
            LintViolation(348, 5, "Move 'join' next to each other.", false),
            LintViolation(410, 13, "Invert 'if' condition.", false),
            LintViolation(419, 13, "Invert 'if' condition.", false),
            LintViolation(474, 8, "Add blank line before block tag group.", false),
            LintViolation(488, 5, "Arrange member 'property' before 'function'.", false),
            LintViolation(496, 5, "Arrange member 'property' before 'function'.", false),
            LintViolation(501, 5, "Arrange member 'property' before 'function'.", false),
            LintViolation(524, 5, "Arrange member 'property' before 'function'.", false),
            LintViolation(538, 74, "Add blank line after multiline branch.", false),
            LintViolation(543, 18, "Add blank line after multiline branch.", false),
            LintViolation(626, 10, "Use common generics 'E, K, N, T, V'.", false),
            LintViolation(645, 5, "Move inner class to the bottom.", false),
            LintViolation(662, 9, "Arrange member 'initializer' before 'constructor'.", false),
            LintViolation(667, 5, "Arrange member 'property' before 'function'.", false),
            LintViolation(690, 10, "Use common generics 'E, K, N, T, V'.", false),
            LintViolation(703, 10, "Use common generics 'E, K, N, T, V'.", false),
            LintViolation(947, 38, "End '@param' with a period.", false),
            LintViolation(948, 48, "End '@param' with a period.", false),
            LintViolation(950, 115, "End '@param' with a period.", false),
            LintViolation(1039, 73, "End '@param' with a period.", false),
            LintViolation(1050, 57, "End '@param' with a period.", false),
            LintViolation(1346, 26, "Omit negation and replace call with 'filterNot'.", false),
            LintViolation(1653, 8, "Add blank line before block tag group.", false),
            LintViolation(1661, 11, "Lift 'else' and add 'return' in 'if' block.", false),
            LintViolation(1669, 8, "Add blank line before block tag group.", false),
            LintViolation(1677, 11, "Lift 'else' and add 'return' in 'if' block.", false),
            LintViolation(1741, 13, "Invert 'if' condition.", false),
        )
    }
}
