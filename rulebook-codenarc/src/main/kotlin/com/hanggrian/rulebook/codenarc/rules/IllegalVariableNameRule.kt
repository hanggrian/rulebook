package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.IllegalVariableNameRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.splitToList
import com.hanggrian.rulebook.codenarc.visitors.RulebookAnyCallVisitor
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ClosureExpression
import org.codehaus.groovy.ast.expr.DeclarationExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MethodCall
import org.codehaus.groovy.ast.expr.TupleExpression
import org.codehaus.groovy.ast.expr.VariableExpression

/** [See detail](https://hanggrian.github.io/rulebook/rules/#illegal-variable-name) */
public class IllegalVariableNameRule : RulebookAstRule() {
    internal var nameList = listOf("object", "integer", "string", "objects", "integers", "strings")

    public var names: String
        get() = throw UnsupportedOperationException()
        set(value) {
            nameList = value.splitToList()
        }

    override fun getName(): String = "IllegalVariableName"

    override fun getAstVisitorClass(): Class<*> = IllegalVariableNameVisitor::class.java

    internal companion object {
        const val MSG = "illegal.variable.name"
    }
}

public class IllegalVariableNameVisitor : RulebookAnyCallVisitor() {
    override fun visitField(node: FieldNode) {
        super.visitField(node)
        process(node, node.name)
    }

    override fun visitDeclarationExpression(node: DeclarationExpression) {
        super.visitDeclarationExpression(node)
        (node.leftExpression as? TupleExpression)
            ?.expressions
            ?.filterIsInstance<VariableExpression>()
            ?.forEach { process(it, it.name) }
    }

    override fun <T> visitAnyCallExpression(node: T) where T : Expression, T : MethodCall {
        (node.arguments as? ArgumentListExpression)
            ?.expressions
            ?.filterIsInstance<ClosureExpression>()
            ?.flatMap { it.parameters?.asList() ?: emptyList() }
            ?.forEach { process(it, it.name) }
    }

    override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
        super.visitConstructorOrMethod(node, isConstructor)
        node.parameters.forEach { process(it, it.name) }
    }

    private fun process(node: ASTNode, name: String) {
        // checks for violation
        val names = (rule as IllegalVariableNameRule).nameList
        name
            .takeIf { it in names }
            ?: return
        addViolation(node, Messages[MSG])
    }
}
