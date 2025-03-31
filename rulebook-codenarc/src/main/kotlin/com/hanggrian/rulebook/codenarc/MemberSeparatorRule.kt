package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.getLineNumberAfter
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.ConstructorNode
import org.codehaus.groovy.ast.FieldNode
import org.codenarc.rule.AbstractAstVisitor
import org.codenarc.rule.Violation

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#member-separator) */
public class MemberSeparatorRule : RulebookAstRule() {
    override fun getName(): String = "MemberSeparator"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "member.separator"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassEx(node: ClassNode) {
            super.visitClassEx(node)

            // collect members
            val members =
                (
                    node.fields.filterNot { it.isStatic } +
                        node.methods.filterNot { it.isStatic } +
                        node.declaredConstructors
                ).sortedBy { it.lineNumber }

            for ((i, member) in members.withIndex()) {
                val lastMember = members.getOrNull(i - 1) ?: continue
                when {
                    // single-line fields can be joined
                    lastMember is FieldNode && member is FieldNode -> {}

                    // checks for violation
                    lastMember.lastLineNumber == getLineNumberAfter(member, lastMember) ->
                        violations +=
                            Violation().apply {
                                rule = this@Visitor.rule
                                lineNumber = lastMember.lastLineNumber
                                sourceLine = sourceCode.line(lastMember.lastLineNumber - 1)
                                message =
                                    Messages.get(
                                        MSG,
                                        when (lastMember) {
                                            is FieldNode -> "property"
                                            is ConstructorNode -> "constructor"
                                            else -> "function"
                                        },
                                    )
                            }
                }
            }
        }
    }
}
