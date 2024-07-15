package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#inner-class-position)
 */
public class InnerClassPositionRule : Rule() {
    override fun getName(): String = "InnerClassPosition"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "inner.class.position"

        private fun List<ASTNode>.isAnyAfter(other: ASTNode): Boolean {
            if (isEmpty()) {
                return false
            }
            return first().lineNumber > other.lineNumber
        }
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassComplete(node: ClassNode) {
            // checks for violation
            for (cls in node.innerClasses) {
                node.takeIf {
                    it.fields.isAnyAfter(cls) ||
                        it.declaredConstructors.isAnyAfter(cls) ||
                        it.methods.isAnyAfter(cls)
                } ?: continue
                addViolation(cls, Messages[MSG])
            }

            super.visitClassComplete(node)
        }
    }
}
