package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.hanggrian.rulebook.ktlint.internals.qualifierName
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DOT_QUALIFIED_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IMPORT_DIRECTIVE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.psi.KtImportDirective

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#qualifier-consistency)
 */
public class QualifierConsistencyRule : Rule("qualifier-consistency") {
    private val importPaths = mutableSetOf<String>()
    private val targetNodes = mutableSetOf<ASTNode>()

    override val tokens: TokenSet =
        TokenSet.create(
            IMPORT_DIRECTIVE,
            TYPE_REFERENCE,
            DOT_QUALIFIED_EXPRESSION,
        )

    override fun visitToken(node: ASTNode, emit: Emit) {
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
