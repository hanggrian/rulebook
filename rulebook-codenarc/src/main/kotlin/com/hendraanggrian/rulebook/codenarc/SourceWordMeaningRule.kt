package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#source-word-meaning)
 */
public class SourceWordMeaningRule : RulebookRule() {
    public var meaninglessWords: String =
        "Util, Utility, Helper, Manager, Wrapper"

    override fun getName(): String = "SourceWordMeaning"

    override fun getAstVisitorClass(): Class<*> = SourceWordMeaningVisitor::class.java
}

public class SourceWordMeaningVisitor : AbstractAstVisitor() {
    override fun visitClassEx(node: ClassNode) {
        // checks for violation
        TITLE_CASE_REGEX.findAll(node.name)
            .filter { it.value in (rule as SourceWordMeaningRule).meaninglessWords.split(", ") }
            .forEach {
                when (val word = it.value) {
                    "Util", "Utility" ->
                        addViolation(
                            node,
                            Messages.get(MSG_UTIL, node.name.substringBefore(word) + 's'),
                        )
                    else -> addViolation(node, Messages.get(MSG_ALL, word))
                }
            }

        super.visitClassEx(node)
    }

    internal companion object {
        const val MSG_ALL = "source.word.meaning.all"
        const val MSG_UTIL = "source.word.meaning.util"

        private val TITLE_CASE_REGEX =
            Regex("((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|([A-Z]+(?=([A-Z][a-z])|(\$)|([0-9]))))")
    }
}
