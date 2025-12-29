package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.visitors.RulebookVisitor
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.ConstructorNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#member-order) */
public class MemberOrderRule : RulebookAstRule() {
    override fun getName(): String = "MemberOrder"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG = "member.order"

        private val ASTNode.memberPosition: Int
            get() =
                when (this) {
                    is FieldNode -> if (isStatic) 4 else 1
                    is ConstructorNode -> 2
                    is MethodNode -> if (isStatic) 4 else 3
                    else -> -1
                }

        private val ASTNode.memberArgument: String
            get() =
                when (this) {
                    is FieldNode -> if (isStatic) "static member" else "property"
                    is ConstructorNode -> "constructor"
                    is MethodNode -> if (isStatic) "static member" else "function"
                    else -> ""
                }
    }

    public class Visitor : RulebookVisitor() {
        override fun visitClassEx(node: ClassNode) {
            super.visitClassEx(node)

            var lastChild: ASTNode? = null
            for (child in (node.declaredConstructors + node.fields + node.methods)
                .sortedBy { it.lineNumber }) {
                // checks for violation
                if ((lastChild?.memberPosition ?: -1) > child.memberPosition) {
                    addViolation(
                        child,
                        Messages.get(MSG, child.memberArgument, lastChild!!.memberArgument),
                    )
                }

                lastChild = child
            }
        }
    }
}
