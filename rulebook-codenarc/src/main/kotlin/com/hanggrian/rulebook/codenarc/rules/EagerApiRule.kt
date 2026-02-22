package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.EagerApiRule.Companion.BUILDSCRIPT_CALLEE
import com.hanggrian.rulebook.codenarc.rules.EagerApiRule.Companion.BUILDSCRIPT_CALL_REPLACEMENT
import com.hanggrian.rulebook.codenarc.rules.EagerApiRule.Companion.DOMAIN_OBJECTS_CALL_REPLACEMENT
import com.hanggrian.rulebook.codenarc.rules.EagerApiRule.Companion.DOMAIN_OBJECT_CALLEES
import com.hanggrian.rulebook.codenarc.rules.EagerApiRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.rules.EagerApiRule.Companion.TASK_CALLEE
import com.hanggrian.rulebook.codenarc.rules.EagerApiRule.Companion.TASK_CALL_REPLACEMENT
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression

/** [See detail](https://hanggrian.github.io/rulebook/rules/#eager-api) */
public class EagerApiRule : RulebookAstRule() {
    override fun getName(): String = "EagerApi"

    override fun getAstVisitorClass(): Class<*> = EagerApiVisitor::class.java

    internal companion object {
        const val MSG = "eager.api"

        const val BUILDSCRIPT_CALLEE = "buildscript"
        const val BUILDSCRIPT_CALL_REPLACEMENT = "plugins"

        val DOMAIN_OBJECT_CALLEES =
            hashSetOf(
                BUILDSCRIPT_CALL_REPLACEMENT,
                "configurations",
                "sourceSets",
            ) + TASK_CALLEE
        val DOMAIN_OBJECTS_CALL_REPLACEMENT =
            hashMapOf(
                "all" to "configureEach",
                "withType" to "configureEach",
                "whenObjectAdded" to "configureEach",
            )

        const val TASK_CALLEE = "tasks"
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
        if (!isFirstVisit(node) && isScript()) {
            return
        }

        val domainBlocks =
            expressions.filter { expr ->
                (expr.objectExpression as? VariableExpression)?.name == "this" &&
                    expr.methodAsString in DOMAIN_OBJECT_CALLEES
            }

        for (expression in expressions) {
            // collect callee
            val callee: String =
                if (expression.methodAsString == BUILDSCRIPT_CALLEE) {
                    BUILDSCRIPT_CALLEE
                } else {
                    val receiverName =
                        (expression.objectExpression as? VariableExpression)
                            ?.name
                            ?: continue
                    if (receiverName != "this") {
                        receiverName
                    } else {
                        domainBlocks
                            .firstOrNull { block ->
                                block !== expression &&
                                    expression.lineNumber >= block.lineNumber &&
                                    expression.lineNumber <= block.lastLineNumber
                            }?.methodAsString
                            ?: continue
                    }
                }

            // checks for violation
            val call = expression.methodAsString
            val callReplacement =
                when (callee) {
                    BUILDSCRIPT_CALLEE -> BUILDSCRIPT_CALL_REPLACEMENT
                    TASK_CALLEE -> TASK_CALL_REPLACEMENT[call]
                    in DOMAIN_OBJECT_CALLEES -> DOMAIN_OBJECTS_CALL_REPLACEMENT[call]
                    else -> null
                } ?: continue
            callReplacement
                .takeUnless { s ->
                    s == "configureEach" &&
                        expressions.any {
                            it.objectExpression === expression &&
                                it.methodAsString == s
                        }
                } ?: continue
            addViolation(expression, Messages[MSG, callReplacement])
        }

        super.visitClassComplete(node)
    }
}
