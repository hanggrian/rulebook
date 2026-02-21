package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.properties.ConfigurableNames
import com.hanggrian.rulebook.codenarc.rules.IllegalVariableNameRule.Companion.MSG
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
public class IllegalVariableNameRule :
    RulebookAstRule(),
    ConfigurableNames {
    override val nameSet: HashSet<String> =
        hashSetOf("object", "integer", "string", "objects", "integers", "strings")

    override fun getName(): String = "IllegalVariableName"

    override fun getAstVisitorClass(): Class<*> = IllegalVariableNameVisitor::class.java

    internal companion object {
        const val MSG = "illegal.variable.name"
    }
}

public class IllegalVariableNameVisitor : RulebookAnyCallVisitor() {
    override fun visitField(node: FieldNode) {
        if (!isFirstVisit(node)) {
            return
        }
        process(node, node.name)
        super.visitField(node)
    }

    override fun visitDeclarationExpression(node: DeclarationExpression) {
        if (!isFirstVisit(node)) {
            return
        }
        (node.leftExpression as? TupleExpression)
            ?.expressions
            ?.filterIsInstance<VariableExpression>()
            ?.forEach { process(it, it.name) }
        super.visitDeclarationExpression(node)
    }

    override fun <T> visitAnyCallExpression(node: T) where T : Expression, T : MethodCall {
        if (!isFirstVisit(node)) {
            return
        }
        (node.arguments as? ArgumentListExpression)
            ?.expressions
            ?.filterIsInstance<ClosureExpression>()
            ?.flatMap { it.parameters?.asList() ?: emptyList() }
            ?.forEach { process(it, it.name) }
    }

    override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
        if (!isFirstVisit(node)) {
            return
        }
        node.parameters.forEach { process(it, it.name) }
        super.visitConstructorOrMethod(node, isConstructor)
    }

    private fun process(node: ASTNode, name: String) {
        // checks for violation
        val names = (rule as IllegalVariableNameRule).nameSet
        name
            .takeIf { it in names }
            ?: return
        addViolation(node, Messages[MSG])
    }
}
