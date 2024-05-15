package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.contains
import com.hendraanggrian.rulebook.ktlint.internals.qualifierName
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DOT_QUALIFIED_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IMPORT_DIRECTIVE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IMPORT_LIST
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.KtImportDirective
import org.jetbrains.kotlin.psi.psiUtil.children

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#qualifier-redundancy)
 */
public class QualifierRedundancyRule : RulebookRule("qualifier-redundancy") {
    public override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        when (node.elementType) {
            TYPE_REFERENCE -> {
                // checks for violation
                node.importStatements.takeIf { node.qualifierName in it } ?: return
                emit(node.startOffset, Messages[MSG], false)
            }
            DOT_QUALIFIED_EXPRESSION -> {
                // only consider top expression and no import line
                node.takeUnless { DOT_QUALIFIED_EXPRESSION in it }
                    ?.takeUnless { it.treeParent.elementType == IMPORT_DIRECTIVE }
                    ?: return

                // checks for violation
                node.importStatements.takeIf { node.qualifierName in it } ?: return
                emit(node.startOffset, Messages[MSG], false)
            }
        }
    }

    internal companion object {
        const val MSG = "qualifier.redundancy"

        // TODO expensive, replace with single root visit
        private val ASTNode.importStatements: Sequence<String>
            get() {
                var importList: ASTNode? = null
                var root = treeParent
                while (root != null) {
                    importList = root.findChildByType(IMPORT_LIST)
                    if (importList != null) {
                        break
                    }
                    root = root.treeParent
                }
                return importList?.children()
                    ?.filter { it.elementType == IMPORT_DIRECTIVE }
                    ?.map { (it.psi as KtImportDirective).importPath!!.pathStr }
                    ?: return emptySequence()
            }
    }
}
