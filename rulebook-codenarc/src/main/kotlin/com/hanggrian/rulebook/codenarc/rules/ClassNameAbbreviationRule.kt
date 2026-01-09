package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.ClassNameAbbreviationRule.Companion.ABBREVIATION_REGEX
import com.hanggrian.rulebook.codenarc.rules.ClassNameAbbreviationRule.Companion.MSG
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#class-name-abbreviation) */
public class ClassNameAbbreviationRule : RulebookAstRule() {
    override fun getName(): String = "ClassNameAbbreviation"

    override fun getAstVisitorClass(): Class<*> = ClassNameAbbreviationVisitor::class.java

    internal companion object {
        const val MSG = "class.name.abbreviation"

        val ABBREVIATION_REGEX = Regex("[A-Z]{3,}")
    }
}

public class ClassNameAbbreviationVisitor : RulebookVisitor() {
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
