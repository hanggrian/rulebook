package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.visitors.RulebookVisitor
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#inner-class-position) */
public class InnerClassPositionRule : RulebookAstRule() {
    override fun getName(): String = "InnerClassPosition"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG = "inner.class.position"

        infix fun List<ASTNode>.areAnyAfter(other: ASTNode) =
            any { it.lineNumber > other.lineNumber }
    }

    public class Visitor : RulebookVisitor() {
        override fun visitClassEx(node: ClassNode) {
            super.visitClassEx(node)

            // checks for violation
            for (`class` in node.innerClasses) {
                node
                    .takeIf {
                        !`class`.isEnum &&
                            !`class`.isAnonymous &&
                            (
                                it.fields areAnyAfter `class` ||
                                    it.declaredConstructors areAnyAfter `class` ||
                                    it.methods areAnyAfter `class`
                            )
                    } ?: continue
                addViolation(`class`, Messages[MSG])
            }
        }
    }
}
