package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import com.hendraanggrian.rulebook.codenarc.internals.RulebookNarc
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/RenameMeaninglessWord).
 */
class RenameMeaninglessWordNarc : RulebookNarc() {
    override fun getName(): String = "RenameMeaninglessWord"

    override fun getAstVisitorClass(): Class<*> = RenameMeaninglessWordVisitor::class.java
}

class RenameMeaninglessWordVisitor : AbstractAstVisitor() {
    override fun visitClassEx(node: ClassNode) {
        // retrieve name
        val matches = TITLE_CASE_REGEX.findAll(node.name)
        val prefix = matches.first().value
        val suffix = matches.last().value

        // find meaningless words
        if (prefix in RESTRICTED_PREFIXES) {
            addViolation(node, Messages.get(MSG_PREFIX, prefix))
        }
        if (suffix in RESTRICTED_SUFFIXES) {
            addViolation(node, Messages.get(MSG_SUFFIX, suffix))
        }
        super.visitClassEx(node)
    }

    internal companion object {
        const val MSG_PREFIX = "rename.meaningless.word.prefix"
        const val MSG_SUFFIX = "rename.meaningless.word.suffix"

        private val RESTRICTED_PREFIXES = setOf("Abstract", "Base", "Generic")
        private val RESTRICTED_SUFFIXES =
            setOf("Util", "Utility", "Manager", "Helper", "Wrapper", "Data", "Info")
        private val TITLE_CASE_REGEX =
            Regex("((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|([A-Z]+(?=([A-Z][a-z])|(\$)|([0-9]))))")
    }
}
