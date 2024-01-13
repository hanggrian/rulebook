package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#source-word-meaning).
 */
public class SourceWordMeaningRule : RulebookRule() {
    public var meaninglessWords: String =
        "Abstract, Base, Util, Utility, Helper, Manager, Wrapper, Data, Info"

    public var ignoredWords: String = ""

    override fun getName(): String = "SourceWordMeaning"

    override fun getAstVisitorClass(): Class<*> = SourceWordMeaningVisitor::class.java
}

public class SourceWordMeaningVisitor : AbstractAstVisitor() {
    override fun visitClassEx(node: ClassNode) {
        // checks for violation
        val meaninglessWords = (rule as SourceWordMeaningRule).meaninglessWords.split(", ")
        val ignoredWords = (rule as SourceWordMeaningRule).ignoredWords.split(", ")
        TITLE_CASE_REGEX.findAll(node.name)
            .filter { it.value in meaninglessWords && it.value !in ignoredWords }
            .forEach { addViolation(node, Messages.get(MSG, it.value)) }

        super.visitClassEx(node)
    }

    internal companion object {
        const val MSG = "source.word.meaning"

        private val TITLE_CASE_REGEX =
            Regex("((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|([A-Z]+(?=([A-Z][a-z])|(\$)|([0-9]))))")
    }
}
