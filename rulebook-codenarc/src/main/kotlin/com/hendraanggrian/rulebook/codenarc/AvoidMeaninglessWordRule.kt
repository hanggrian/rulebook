package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#avoid-meaningless-word).
 */
public class AvoidMeaninglessWordRule : RulebookRule() {
    public var words: String = "Abstract, Base, Util, Utility, Helper, Manager, Wrapper, Data, Info"
    public var exclude: String = ""

    public override fun getName(): String = "AvoidMeaninglessWord"

    public override fun getAstVisitorClass(): Class<*> = AvoidMeaninglessWordVisitor::class.java
}

public class AvoidMeaninglessWordVisitor : AbstractAstVisitor() {
    public override fun visitClassEx(node: ClassNode) {
        // checks for violation
        TITLE_CASE_REGEX.findAll(node.name)
            .filter {
                it.value in (rule as AvoidMeaninglessWordRule).words.split(", ") &&
                    it.value !in (rule as AvoidMeaninglessWordRule).exclude.split(", ")
            }
            .forEach { addViolation(node, Messages.get(MSG, it.value)) }
        super.visitClassEx(node)
    }

    internal companion object {
        const val MSG = "avoid.meaningless.word"

        private val TITLE_CASE_REGEX =
            Regex("((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|([A-Z]+(?=([A-Z][a-z])|(\$)|([0-9]))))")
    }
}
