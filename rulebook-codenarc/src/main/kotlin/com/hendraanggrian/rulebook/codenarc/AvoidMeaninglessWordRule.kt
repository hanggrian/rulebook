package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#avoid-meaningless-word).
 */
public class AvoidMeaninglessWordRule : RulebookRule() {
    public override fun getName(): String = "AvoidMeaninglessWord"

    public override fun getAstVisitorClass(): Class<*> = AvoidMeaninglessWordVisitor::class.java
}

public class AvoidMeaninglessWordVisitor : AbstractAstVisitor() {
    public override fun visitClassEx(node: ClassNode) {
        // Retrieve name.
        val matches = TITLE_CASE_REGEX.findAll(node.name)
        val prefix = matches.first().value
        val suffix = matches.last().value

        // Find meaningless words.
        if (prefix in RESTRICTED_PREFIXES) {
            addViolation(node, Messages.get(MSG_PREFIX, prefix))
        }
        if (suffix in RESTRICTED_SUFFIXES) {
            addViolation(node, Messages.get(MSG_SUFFIX, suffix))
        }
        super.visitClassEx(node)
    }

    internal companion object {
        const val MSG_PREFIX = "avoid.meaningless.word.prefix"
        const val MSG_SUFFIX = "avoid.meaningless.word.suffix"

        private val RESTRICTED_PREFIXES = setOf("Abstract", "Base", "Generic")
        private val RESTRICTED_SUFFIXES =
            setOf("Util", "Utility", "Manager", "Helper", "Wrapper", "Data", "Info")
        private val TITLE_CASE_REGEX =
            Regex("((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|([A-Z]+(?=([A-Z][a-z])|(\$)|([0-9]))))")
    }
}
