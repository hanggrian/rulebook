package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.AbbreviationAsWordRule.Companion.ABBREVIATION_REGEX
import com.hanggrian.rulebook.codenarc.rules.AbbreviationAsWordRule.Companion.MSG
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#abbreviation-as-word) */
public class AbbreviationAsWordRule : RulebookAstRule() {
    override fun getName(): String = "AbbreviationAsWord"

    override fun getAstVisitorClass(): Class<*> = AbbreviationAsWordVisitor::class.java

    internal companion object {
        const val MSG = "abbreviation.as.word"

        val ABBREVIATION_REGEX = Regex("[A-Z]{3,}")
    }
}

public class AbbreviationAsWordVisitor : RulebookVisitor() {
    override fun visitClassEx(node: ClassNode) {
        if (!isFirstVisit(node)) {
            return
        }

        // checks for violation
        process(node, node.name)

        super.visitClassEx(node)
    }

    override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
        if (!isFirstVisit(node)) {
            return
        }

        // checks for violation
        node.parameters.forEach { process(it, it.name) }
        process(node, node.name)

        super.visitConstructorOrMethod(node, isConstructor)
    }

    private fun process(node: ASTNode, name: String) {
        val transformation =
            name
                .takeIf { ABBREVIATION_REGEX.containsMatchIn(it) }
                ?.let { s ->
                    ABBREVIATION_REGEX.replace(s) {
                        it.value.first() +
                            when {
                                it.range.last == s.lastIndex -> it.value.drop(1).lowercase()

                                else ->
                                    it.value.drop(1).dropLast(1).lowercase() +
                                        it.value.last()
                            }
                    }
                } ?: return
        addViolation(node, Messages[MSG, transformation])
    }
}
