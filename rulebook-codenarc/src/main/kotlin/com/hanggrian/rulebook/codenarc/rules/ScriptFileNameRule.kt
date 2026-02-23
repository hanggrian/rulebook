package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.ScriptFileNameRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.rules.ScriptFileNameRule.Companion.REGEX
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.Violation
import java.io.File

/** [See detail](https://hanggrian.github.io/rulebook/rules/#script-file-name) */
public class ScriptFileNameRule : RulebookAstRule() {
    override fun getName(): String = "ScriptFileName"

    override fun getAstVisitorClass(): Class<*> = ScriptFileNameVisitor::class.java

    internal companion object {
        const val MSG = "script.file.name"

        val REGEX = Regex("([a-z0-9])([A-Z])")
    }
}

public class ScriptFileNameVisitor : RulebookVisitor() {
    override fun visitClassComplete(node: ClassNode) {
        if (!isFirstVisit(node) || !isScript()) {
            return
        }

        // checks for violation
        val name =
            sourceCode
                .name
                .substringAfterLast(File.separator)
                .substringBefore(".gradle")
        val nameReplacement =
            name
                .replace(REGEX, "$1-$2")
                .replace('_', '-')
                .lowercase()
                .takeUnless { it == name }
                ?: return
        addViolation(
            Violation().also {
                it.rule = rule
                it.lineNumber = 1
                it.sourceLine = sourceCode.line(0)
                it.message = Messages[MSG, nameReplacement]
            },
        )

        super.visitClassComplete(node)
    }
}
