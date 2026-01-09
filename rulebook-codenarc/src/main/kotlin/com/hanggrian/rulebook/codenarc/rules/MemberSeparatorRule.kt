package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.createViolation
import com.hanggrian.rulebook.codenarc.getLineNumberBefore
import com.hanggrian.rulebook.codenarc.rules.MemberSeparatorRule.Companion.MSG
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.ConstructorNode
import org.codehaus.groovy.ast.FieldNode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#member-separator) */
public class MemberSeparatorRule : RulebookAstRule() {
    override fun getName(): String = "MemberSeparator"

    override fun getAstVisitorClass(): Class<*> = MemberSeparatorVisitor::class.java

    internal companion object {
        const val MSG = "member.separator"
    }
}

public class MemberSeparatorVisitor : RulebookVisitor() {
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
                lastMember.lastLineNumber == getLineNumberBefore(member, lastMember) ->
                    violations +=
                        rule.createViolation(
                            lastMember.lastLineNumber,
                            lastSourceLine(lastMember),
                            Messages[
                                MSG,
                                when (lastMember) {
                                    is FieldNode -> "property"
                                    is ConstructorNode -> "constructor"
                                    else -> "function"
                                },
                            ],
                        )
            }
        }
    }
}
