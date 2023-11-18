package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DOT_QUALIFIED_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IMPORT_DIRECTIVE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#use-kotlin-api).
 */
public class UseKotlinApiRule : RulebookRule("use-kotlin-api") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        when (node.elementType) {
            IMPORT_DIRECTIVE -> {
                // get text after `import`
                val text = node.text.substringAfterLast(' ')

                // checks for violation
                val kotlinClassReplacement = text.kotlinClassReplacement ?: return
                val dotQualifiedExpression =
                    node.findChildByType(DOT_QUALIFIED_EXPRESSION) ?: return
                emit(
                    dotQualifiedExpression.startOffset,
                    Messages.get(MSG_IMPORT, kotlinClassReplacement),
                    false,
                )
            }
            TYPE_REFERENCE -> {
                // get text without argument and nullability
                val text = node.text.substringBefore('<').substringBefore('?')

                // checks for violation
                val kotlinClassReplacement = text.kotlinClassReplacement ?: return
                emit(
                    node.startOffset,
                    Messages.get(MSG_REFERENCE, kotlinClassReplacement),
                    false,
                )
            }
        }
    }

    internal companion object {
        const val MSG_IMPORT = "use.kotlin.api.import"
        const val MSG_REFERENCE = "use.kotlin.api.reference"

        private val String.kotlinClassReplacement: String?
            get() {
                if (!startsWith("java.lang.") && !startsWith("java.util.")) {
                    return null
                }
                val qualifiedName =
                    try {
                        Class.forName(this).kotlin.qualifiedName ?: return null
                    } catch (e: ClassNotFoundException) {
                        return null
                    }
                return qualifiedName.takeIf { it.startsWith("kotlin.") }
            }
    }
}
