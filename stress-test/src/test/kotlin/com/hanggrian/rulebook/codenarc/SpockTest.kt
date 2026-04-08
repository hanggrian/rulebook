package com.hanggrian.rulebook.codenarc

import kotlin.test.Test

class SpockTest : AllRulesTest() {
    @Test
    fun org_spockframework_gradle_AsciiDocLinkVerifier() =
        assertViolations(
            getCode("spock/AsciiDocLinkVerifier.groovy"),
            violationOf(23, "class AsciiDocLinkVerifier {", "Put 'final' modifier."),
            violationOf(23, "class AsciiDocLinkVerifier {", "Add private constructor."),
            violationOf(38, ".tap {", "Omit newline before '.'."),
            violationOf(58, "def relativeLinkTargets = subject", "Put newline before '='."),
            violationOf(69, "it.port == -1", "Replace equality with 'is()'."),
            violationOf(73, "def result = relativeLinkTargets", "Put newline before '='."),
            violationOf(97, ".findAll()", "Omit newline before '.'."),
            violationOf(97, ".findAll()", "Put trailing comma."),
            violationOf(107, ".findAll()", "Put trailing comma."),
            violationOf(111, "return result + subject", "Put newline after operator '+'."),
            violationOf(121, ".tap {", "Omit newline before '.'."),
        )
}
