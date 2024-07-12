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
            // get first inner class
            var innerClass: ClassNode? = null
            for (c in node.innerClasses) {
                innerClass = c
                break
            }
            if (innerClass == null) {
                return super.visitClassComplete(node)
            }

            // checks for violation
            node
                .takeIf {
                    it.fields.isAnyAfter(innerClass) ||
                        it.declaredConstructors.isAnyAfter(innerClass) ||
                        it.methods.isAnyAfter(innerClass)
                } ?: return super.visitClassComplete(node)
            addViolation(innerClass, Messages[MSG])

            super.visitClassComplete(node)
        }
    }
}
