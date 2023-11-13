package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DOT_QUALIFIED_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IMPORT_DIRECTIVE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IMPORT_LIST
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import org.ec4j.core.model.PropertyType.root
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.children

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#remove-redundant-qualifier).
 */
class RemoveRedundantQualifierRule : RulebookRule("remove-redundant-qualifier") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // First line of filter.
        when (node.elementType) {
            TYPE_REFERENCE -> {
                // Get text without argument and nullability.
                val typeText = node.text.substringBefore('<').substringBefore('?')

                node.importDirectives.forEach {
                    // Get text after `import`.
                    val importText = it.text.substringAfterLast(' ')

                    // Checks for violation.
                    if (typeText == importText) {
                        emit(node.startOffset, Messages[MSG], false)
                    }
                }
            }
            DOT_QUALIFIED_EXPRESSION -> {
                // Only consider top expression.
                if (DOT_QUALIFIED_EXPRESSION in node) {
                    return
                }

                // But skip import line.
                if (node.treeParent.elementType == IMPORT_DIRECTIVE) {
                    return
                }

                // Get text without argument and nullability.
                val typeText = node.text.substringBefore('<').substringBefore('?')

                node.importDirectives.forEach {
                    // Get text after `import`.
                    val importText = it.text.substringAfterLast(' ')

                    // Checks for violation.
                    if (typeText.startsWith(importText)) {
                        emit(node.startOffset, Messages[MSG], false)
                    }
                }
            }
        }
    }

    internal companion object {
        const val MSG = "remove.redundant.qualifier"

        private val ASTNode.importDirectives: Sequence<ASTNode>
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
                    ?: return emptySequence()
            }
    }
}
