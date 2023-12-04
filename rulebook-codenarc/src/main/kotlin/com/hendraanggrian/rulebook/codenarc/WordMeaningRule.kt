package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#word-meaning).
 */
public class WordMeaningRule : RulebookRule() {
    public var meaninglessWords: String =
        "Abstract, Base, Util, Utility, Helper, Manager, Wrapper, Data, Info"

    public var ignoredWords: String = ""

    public override fun getName(): String = "WordMeaning"

    public override fun getAstVisitorClass(): Class<*> = WordMeaningVisitor::class.java
}

public class WordMeaningVisitor : AbstractAstVisitor() {
    public override fun visitClassEx(node: ClassNode) {
        // checks for violation
        TITLE_CASE_REGEX.findAll(node.name)
            .filter {
                it.value in (rule as WordMeaningRule).meaninglessWords.split(", ") &&
                    it.value !in (rule as WordMeaningRule).ignoredWords.split(", ")
            }
            .forEach { addViolation(node, Messages.get(MSG, it.value)) }
        super.visitClassEx(node)
    }

    internal companion object {
        const val MSG = "word.meaning"

        private val TITLE_CASE_REGEX =
            Regex("((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|([A-Z]+(?=([A-Z][a-z])|(\$)|([0-9]))))")
    }
}
