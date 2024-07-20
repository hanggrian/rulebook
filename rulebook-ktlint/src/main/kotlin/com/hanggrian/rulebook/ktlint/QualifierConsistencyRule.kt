package com.hanggrian.rulebook.ktlint

import com.google.common.collect.HashMultimap
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.qualifierName
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CALL_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DOT_QUALIFIED_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IMPORT_DIRECTIVE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.isRoot
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.psi.KtImportDirective

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#qualifier-consistency)
 */
public class QualifierConsistencyRule : Rule("qualifier-consistency") {
    private val importPaths = mutableSetOf<String>()
    private val targetNodes = HashMultimap.create<ASTNode, ASTNode>()

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
        when (node.elementType) {
            // keep class qualifier
            TYPE_REFERENCE -> targetNodes.put(node, null)
            // keep class qualifier and calling method
            else -> {
                val dot =
                    node
                        .takeUnless { it.treeParent.elementType == IMPORT_DIRECTIVE }
                        ?.findChildByType(DOT_QUALIFIED_EXPRESSION)
                        ?: return
                targetNodes.put(dot, null)
                targetNodes.put(
                    dot,
                    node
                        .findChildByType(CALL_EXPRESSION)
                        ?.findChildByType(REFERENCE_EXPRESSION)
                        ?.findChildByType(IDENTIFIER)
                        ?: return,
                )
            }
        }
    }

    override fun afterVisitChildNodes(node: ASTNode, emit: Emit) {
        // only trigger once
        if (!node.isRoot()) {
            return
        }

        // checks for violation
        targetNodes.forEach { `class`, method ->
            if (when (method) {
                    null -> `class`.qualifierName in importPaths
                    else -> `class`.qualifierName + '.' + method.text in importPaths
                }
            ) {
                emit(`class`.startOffset, Messages[MSG], false)
            }
        }
    }

    internal companion object {
        const val MSG = "qualifier.consistency"
    }
}
