package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.contains
import com.hendraanggrian.rulebook.ktlint.internals.qualifierName
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DOT_QUALIFIED_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IMPORT_DIRECTIVE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.KtImportDirective
import org.jetbrains.kotlin.utils.addToStdlib.ifTrue

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#qualifier-redundancy).
 */
public class QualifierRedundancyRule : RulebookRule("qualifier-redundancy") {
    private val imports = mutableListOf<String>()

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // gather import paths
        node.takeIf { it.elementType == IMPORT_DIRECTIVE }
            ?.let { imports += (it.psi as KtImportDirective).importPath!!.pathStr }
    }

    override fun afterVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != TYPE_REFERENCE &&
            node.elementType != DOT_QUALIFIED_EXPRESSION
        ) {
            return
        }

        // only consider top expression and no import line
        when (node.elementType) {
            DOT_QUALIFIED_EXPRESSION ->
                node.takeUnless { DOT_QUALIFIED_EXPRESSION in it }
                    ?.takeUnless { it.treeParent.elementType == IMPORT_DIRECTIVE }
                    ?: return
        }

        // checks for violation
        (node.qualifierName in imports)
            .ifTrue { emit(node.startOffset, Messages[MSG], false) }
    }

    internal companion object {
        const val MSG = "qualifier.redundancy"
    }
}
