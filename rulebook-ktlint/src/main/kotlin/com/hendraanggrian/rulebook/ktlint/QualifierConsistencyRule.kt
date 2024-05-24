package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.contains
import com.hendraanggrian.rulebook.ktlint.internals.qualifierName
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DOT_QUALIFIED_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IMPORT_DIRECTIVE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.KtImportDirective

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#qualifier-consistency)
 */
public class QualifierConsistencyRule : RulebookRule("qualifier-consistency") {
    private val importPaths = mutableSetOf<String>()
    private val targetNodes = mutableSetOf<ASTNode>()

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        when (node.elementType) {
            IMPORT_DIRECTIVE ->
                // keep import list
                importPaths += (node.psi as KtImportDirective).importPath!!.pathStr
            TYPE_REFERENCE ->
                // keep expressions
                targetNodes += node
            DOT_QUALIFIED_EXPRESSION -> {
                // only consider top expression and no import line
                node.takeUnless { DOT_QUALIFIED_EXPRESSION in it }
                    ?.takeUnless { it.treeParent.elementType == IMPORT_DIRECTIVE }
                    ?: return

                // keep expressions
                targetNodes += node
            }
        }
    }

    override fun afterVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
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
