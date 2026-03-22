package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test
import kotlin.test.assertIs

class ScriptFileNameRuleTest : RuleTest<ScriptFileNameRule>() {
    override fun createRule() = ScriptFileNameRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<ScriptFileNameVisitor>(rule.astVisitor)
    }

    @Test
    fun `kebab-case file name`() {
        sourceCodeName = "my-file.gradle"
        assertNoViolations("")
    }

    @Test
    fun `PascalCase file name`() {
        sourceCodeName = "MyFile.gradle"
        assertSingleViolation("", 1, "", "Rename file to 'my-file'.")
    }
}
