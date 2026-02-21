package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import kotlin.collections.plusAssign

/** [See detail](https://hanggrian.github.io/rulebook/rules/#eager-api) */
public class EagerApiRule : RulebookAstRule() {
    override fun getName(): String = "EagerApi"

    override fun getAstVisitorClass(): Class<*> = EagerApiVisitor::class.java

    internal companion object {
        const val MSG = "eager.api"

        const val TASK_CALLEE = "tasks"

        val DOMAIN_OBJECT_CALLEES =
            hashSetOf(
                "plugins",
                "configurations",
                "sourceSets",
            ) + TASK_CALLEE

        val DOMAIN_OBJECTS_CALL_REPLACEMENT =
            hashMapOf(
                "all" to "configureEach",
                "withType" to "configureEach",
                "whenObjectAdded" to "configureEach",
            )

        val TASK_CALL_REPLACEMENT =
            hashMapOf(
                "create" to "register",
                "findByName" to "named",
                "getByName" to "named",
                "getByName" to "named",
            ) + DOMAIN_OBJECTS_CALL_REPLACEMENT
    }
}

public class EagerApiVisitor : RulebookVisitor() {
    private val expressions = mutableListOf<MethodCallExpression>()

    override fun visitMethodCallExpression(node: MethodCallExpression) {
        if (isFirstVisit(node) && isScript()) {
            expressions += node
        }
        super.visitMethodCallExpression(node)
    }

    override fun visitClassComplete(node: ClassNode) {
        if (!isFirstVisit(node)) {
            return
        }

        val domainBlocks =
            expressions.filter { expr ->
                (expr.objectExpression as? VariableExpression)?.name == "this" &&
                    expr.methodAsString in EagerApiRule.DOMAIN_OBJECT_CALLEES
            }

        for (expression in expressions) {
            // collect callee
            val receiverName =
                (expression.objectExpression as? VariableExpression)
                    ?.name
                    ?: continue
            val callee =
                when {
                    receiverName != "this" -> receiverName

                    else ->
                        domainBlocks
                            .firstOrNull { block ->
                                block !== expression &&
                                    expression.lineNumber >= block.lineNumber &&
                                    expression.lineNumber <= block.lastLineNumber
                            }?.methodAsString
                            ?: continue
                }

            // checks for violation
            val call = expression.methodAsString
            val callReplacement =
                when (callee) {
                    EagerApiRule.TASK_CALLEE -> EagerApiRule.TASK_CALL_REPLACEMENT[call]

                    in EagerApiRule.DOMAIN_OBJECT_CALLEES ->
                        EagerApiRule.DOMAIN_OBJECTS_CALL_REPLACEMENT[call]

                    else -> null
                } ?: continue
            if (callReplacement == "configureEach" &&
                expressions.any {
                    it.objectExpression === expression &&
                        it.methodAsString == callReplacement
                }
            ) {
                continue
            }
            addViolation(expression, Messages[EagerApiRule.MSG, callReplacement])
        }

        super.visitClassComplete(node)
    }
}
