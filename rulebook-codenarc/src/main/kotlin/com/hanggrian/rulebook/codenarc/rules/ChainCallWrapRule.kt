package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.ChainCallWrapRule.Companion.MSG_MISSING
import com.hanggrian.rulebook.codenarc.rules.ChainCallWrapRule.Companion.MSG_UNEXPECTED
import com.hanggrian.rulebook.codenarc.rules.ChainCallWrapRule.Companion.identifierOrSelf
import com.hanggrian.rulebook.codenarc.rules.ChainCallWrapRule.Companion.parent
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ConstructorCallExpression
import org.codehaus.groovy.ast.expr.DeclarationExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.PropertyExpression
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ExpressionStatement

/** [See detail](https://hanggrian.github.io/rulebook/rules/#chain-call-wrap) */
public class ChainCallWrapRule : RulebookAstRule() {
    override fun getName(): String = "ChainCallWrap"

    override fun getAstVisitorClass(): Class<*> = ChainCallWrapVisitor::class.java

    internal companion object {
        const val MSG_MISSING = "chain.call.wrap.missing"
        const val MSG_UNEXPECTED = "chain.call.wrap.unexpected"

        val Expression.parent: Expression?
            get() =
                (this as? MethodCallExpression)?.objectExpression
                    ?: (this as? PropertyExpression)?.objectExpression

        val Expression.identifierOrSelf: Expression
            get() =
                (this as? MethodCallExpression)?.method
                    ?: (this as? PropertyExpression)?.property
                    ?: (this as? ConstructorCallExpression)?.arguments
                    ?: this
    }
}

// do not use `visitMethodCallExpression` because there is no way to determine return call
public class ChainCallWrapVisitor : RulebookVisitor() {
    override fun visitBlockStatement(node: BlockStatement) {
        if (!isFirstVisit(node)) {
            return
        }

        for (statement in node.statements.filterIsInstance<ExpressionStatement>()) {
            process(
                (statement.expression as? DeclarationExpression)?.rightExpression
                    ?: statement.expression,
            )
        }

        super.visitBlockStatement(node)
    }

    override fun visitMethodCallExpression(node: MethodCallExpression) {
        if (!isFirstVisit(node)) {
            return
        }

        (node.arguments as? ArgumentListExpression)
            ?.filterIsInstance<MethodCallExpression>()
            ?.forEach(::process)

        super.visitMethodCallExpression(node)
    }

    private fun process(node: Expression) {
        // collect dots
        val expressions =
            generateSequence(node) { it.parent }
                .takeWhile { it is MethodCallExpression || it is PropertyExpression }

        // skip dots in single-line
        val firstIdentifier = expressions.firstOrNull()?.identifierOrSelf ?: return
        expressions
            .takeUnless { e ->
                e.all { it.identifierOrSelf.lineNumber == firstIdentifier.lineNumber }
            } ?: return

        // checks for violation
        for (expression in expressions) {
            val parent =
                expression
                    .parent
                    ?.takeUnless { it.lastLineNumber < 0 }
                    ?: continue
            val line = lastSourceLine(parent).trimStart()
            val identifier = expression.identifierOrSelf
            if ((line.startsWith(')') || line.startsWith('}')) &&
                parent.lastLineNumber != parent.identifierOrSelf.lineNumber
            ) {
                identifier
                    .takeUnless { it.lineNumber == parent.lastLineNumber }
                    ?.let { addViolation(it, Messages[MSG_UNEXPECTED]) }
                continue
            }
            identifier
                .takeUnless { it.lineNumber == parent.lastLineNumber + 1 }
                ?.let { addViolation(it, Messages[MSG_MISSING]) }
        }
    }
}
