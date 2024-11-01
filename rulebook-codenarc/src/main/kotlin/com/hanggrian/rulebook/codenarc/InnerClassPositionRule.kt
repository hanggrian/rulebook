package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#inner-class-position) */
public class InnerClassPositionRule : RulebookRule() {
    override fun getName(): String = "InnerClassPosition"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "inner.class.position"

        private fun List<ASTNode>.isAnyAfter(other: ASTNode): Boolean =
            takeUnless { it.isEmpty() }
                ?.let { it.first().lineNumber > other.lineNumber }
                ?: false
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassComplete(node: ClassNode) {
            super.visitClassComplete(node)

            // checks for violation
            for (`class` in node.innerClasses) {
                node
                    .takeIf {
                        it.fields.isAnyAfter(`class`) ||
                            it.declaredConstructors.isAnyAfter(`class`) ||
                            it.methods.isAnyAfter(`class`)
                    } ?: continue
                addViolation(`class`, Messages[MSG])
            }
        }
    }
}
