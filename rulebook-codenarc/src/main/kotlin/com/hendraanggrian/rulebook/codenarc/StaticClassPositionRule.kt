package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor
import java.lang.reflect.Modifier.isStatic

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#static-class-position)
 */
public class StaticClassPositionRule : RulebookRule() {
    override fun getName(): String = "StaticClassPosition"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassComplete(node: ClassNode) {
            // get first inner static class
            var innerClass: ClassNode? = null
            for (c in node.innerClasses) {
                if (!isStatic(c.modifiers)) {
                    continue
                }
                innerClass = c
                break
            }
            if (innerClass == null) {
                return super.visitClassComplete(node)
            }

            // checks for violation
            node.takeIf {
                it.fields.isAnyAfter(innerClass) ||
                    it.declaredConstructors.isAnyAfter(innerClass) ||
                    it.methods.isAnyAfter(innerClass)
            } ?: return super.visitClassComplete(node)
            addViolation(innerClass, Messages[MSG])

            super.visitClassComplete(node)
        }
    }

    internal companion object {
        const val MSG = "static.class.position"

        private fun List<ASTNode>.isAnyAfter(other: ASTNode): Boolean {
            if (isEmpty()) {
                return false
            }
            return first().lineNumber > other.lineNumber
        }
    }
}
