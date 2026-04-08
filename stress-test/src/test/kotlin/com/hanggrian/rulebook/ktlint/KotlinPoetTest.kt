package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class KotlinPoetTest : AllRulesTest() {
    @Test
    fun com_squareup_kotlinpoet_FileSpec() {
        assertThatCodeAll(getCode("kotlinpoet/FileSpec.kt"))
            .hasLintViolationsForAdditionalRule(
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
}
