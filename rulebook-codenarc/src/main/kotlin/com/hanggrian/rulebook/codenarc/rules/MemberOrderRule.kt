package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.properties.ConfigurableOrder
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.ConstructorNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#member-order) */
public class MemberOrderRule :
    RulebookAstRule(),
    ConfigurableOrder {
    override val orderSet: LinkedHashSet<String> =
        linkedSetOf("property", "constructor", "function", "static")
    internal var propertyPosition = 0
    internal var constructorPosition = 1
    internal var functionPosition = 2
    internal var staticPosition = 3

    override fun setOrder(order: String) {
        super.setOrder(order)
        require(orderSet.size == 4)

        propertyPosition = orderSet.indexOf("property")
        constructorPosition = orderSet.indexOf("constructor")
        functionPosition = orderSet.indexOf("function")
        staticPosition = orderSet.indexOf("static")
    }

    override fun getName(): String = "MemberOrder"

    override fun getAstVisitorClass(): Class<*> = MemberOrderVisitor::class.java

    internal companion object {
        const val MSG = "member.order"
    }
}

public class MemberOrderVisitor : RulebookVisitor() {
    private val ASTNode.memberPosition: Int
        get() {
            val r = rule as MemberOrderRule
            return when (this) {
                is FieldNode -> if (isStatic) r.staticPosition else r.propertyPosition
                is ConstructorNode -> r.constructorPosition
                else -> if ((this as MethodNode).isStatic) r.staticPosition else r.functionPosition
            }
        }

    private val ASTNode.memberArgument: String
        get() =
            when (this) {
                is FieldNode -> if (isStatic) "static member" else "property"
                is ConstructorNode -> "constructor"
                else -> if ((this as MethodNode).isStatic) "static member" else "function"
            }

    override fun visitClassEx(node: ClassNode) {
        super.visitClassEx(node)

        // in Groovy, static members have specific keyword
        var lastChild: ASTNode? = null
        for (child in (node.fields + node.methods + node.declaredConstructors)
            .sortedBy { it.lineNumber }) {
            // checks for violation
            if ((lastChild?.memberPosition ?: -1) > child.memberPosition) {
                addViolation(
                    child,
                    Messages[MemberOrderRule.MSG, child.memberArgument, lastChild!!.memberArgument],
                )
            }

            lastChild = child
        }
    }
}
