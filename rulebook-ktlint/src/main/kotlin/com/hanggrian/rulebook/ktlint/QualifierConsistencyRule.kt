package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Emit
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.hanggrian.rulebook.ktlint.internals.qualifierName
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DOT_QUALIFIED_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IMPORT_DIRECTIVE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.KtImportDirective

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#qualifier-consistency)
 */
public class QualifierConsistencyRule :
    Rule("qualifier-consistency"),
    RuleAutocorrectApproveHandler {
    private val importPaths = mutableSetOf<String>()
    private val targetNodes = mutableSetOf<ASTNode>()

    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != IMPORT_DIRECTIVE &&
            node.elementType != TYPE_REFERENCE &&
            node.elementType != DOT_QUALIFIED_EXPRESSION
        ) {
            return
        }

        // keep import list and expressions
        if (node.elementType == IMPORT_DIRECTIVE) {
            importPaths += (node.psi as KtImportDirective).importPath!!.pathStr
            return
        }
        targetNodes +=
            when (node.elementType) {
                TYPE_REFERENCE -> node
                else ->
                    node
                        .takeUnless { DOT_QUALIFIED_EXPRESSION in it }
                        ?.takeUnless { it.treeParent.elementType == IMPORT_DIRECTIVE }
                        ?: return
            }
    }

    override fun afterVisitChildNodes(node: ASTNode, emit: Emit) {
        // only trigger once
        if (node.elementType != FILE) {
            return
        }

        // checks for violation
        for (targetNode in targetNodes) {
            if (targetNode.qualifierName !in importPaths) {
                continue
            }
            emit(targetNode.startOffset, Messages[MSG], false)
        }
    }

    internal companion object {
        const val MSG = "qualifier.consistency"
    }
}
