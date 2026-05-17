package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ExposedTest : AllRulesTest() {
    @Test
    fun org_jetbrains_exposed_dsl_Table() {
        assertThatCodeAll(getCode("exposed/Table.kt"))
            .hasLintViolationsForAdditionalRule(
                LintViolation(1, 1, "Reduce file size to '1,000'.", false),
                LintViolation(30, 1, "Convert into single-line.", false),
                LintViolation(40, 5, "Convert into single-line.", false),
                LintViolation(61, 1, "Convert into single-line.", false),
                LintViolation(67, 5, "Arrange member 'property' before 'function'.", false),
                LintViolation(211, 1, "Convert into single-line.", false),
                LintViolation(216, 1, "Convert into single-line.", false),
                LintViolation(236, 1, "Convert into single-line.", false),
                LintViolation(257, 5, "Arrange member 'property' before 'function'.", false),
                LintViolation(348, 5, "Move 'join' next to each other.", false),
                LintViolation(365, 10, "Use 'isEmpty' and replace call with 'filterNot'.", false),
                LintViolation(366, 10, "Use 'isEmpty' and replace call with 'takeUnless'.", false),
                LintViolation(410, 13, "Invert 'if' condition.", false),
                LintViolation(419, 13, "Invert 'if' condition.", false),
                LintViolation(474, 8, "Add blank line before block tag group.", false),
                LintViolation(488, 5, "Arrange member 'property' before 'function'.", false),
                LintViolation(496, 5, "Arrange member 'property' before 'function'.", false),
                LintViolation(501, 5, "Arrange member 'property' before 'function'.", false),
                LintViolation(524, 5, "Arrange member 'property' before 'function'.", false),
                LintViolation(645, 5, "Move inner class to the bottom.", false),
                LintViolation(662, 9, "Arrange member 'initializer' before 'constructor'.", false),
                LintViolation(667, 5, "Arrange member 'property' before 'function'.", false),
                LintViolation(690, 10, "Use pascal-case name.", false),
                LintViolation(703, 10, "Use pascal-case name.", false),
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
                LintViolation(1801, 9, "Omit redundant 'if' condition.", false),
            )
    }
}
