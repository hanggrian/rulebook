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
        super.visitClassEx(node)

        // checks for violation
        process(node, node.name)
    }

    override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
        super.visitConstructorOrMethod(node, isConstructor)

        // checks for violation
        node.parameters.forEach { process(it, it.name) }
        process(node, node.name)
    }

    private fun process(node: ASTNode, name: String) {
        val transformation =
            name
                .takeIf { AbbreviationAsWordRule.ABBREVIATION_REGEX.containsMatchIn(it) }
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
