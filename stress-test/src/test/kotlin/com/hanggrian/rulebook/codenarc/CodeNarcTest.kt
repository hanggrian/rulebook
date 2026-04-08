package com.hanggrian.rulebook.codenarc

import kotlin.test.Test

class CodeNarcTest : AllRulesTest() {
    @Test
    fun org_codenarc_CodeNarcRunner() =
        assertViolations(
            getCode("codenarc/CodeNarcRunner.groovy"),
            violationOf(
                49,
                "String ruleSetFiles",
                "Arrange member 'property' before 'static member'.",
            ),
            violationOf(
                116,
                "if (pluginClassNames) {",
                "Invert 'if' condition.",
            ),
            violationOf(
                143,
                "List<Rule> rules = new ArrayList(ruleSet.getRules())    // need it mutable",
                "Remove consecutive whitespace.",
            ),
            violationOf(
                149,
                "if (plugins) {",
                "Invert 'if' condition.",
            ),
            violationOf(
                167,
                "* @return a single RuleSet",
                "End '@return' with a period.",
            ),
        )
}
