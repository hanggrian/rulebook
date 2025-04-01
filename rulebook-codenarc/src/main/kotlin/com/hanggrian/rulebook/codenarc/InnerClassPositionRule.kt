package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/#inner-class-position) */
public class InnerClassPositionRule : RulebookAstRule() {
    override fun getName(): String = "InnerClassPosition"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "inner.class.position"

        private infix fun List<ASTNode>.areAnyAfter(other: ASTNode) =
            any { it.lineNumber > other.lineNumber }
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassEx(node: ClassNode) {
            super.visitClassEx(node)

            // checks for violation
            for (`class` in node.innerClasses) {
                node
                    .takeUnless { `class`.isEnum }
                    ?.takeIf {
                        it.fields areAnyAfter `class` ||
                            it.declaredConstructors areAnyAfter `class` ||
                            it.methods areAnyAfter `class`
                    } ?: continue
                addViolation(`class`, Messages[MSG])
            }
        }
    }
}
