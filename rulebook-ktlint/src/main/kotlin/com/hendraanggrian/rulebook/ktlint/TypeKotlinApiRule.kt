package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.DOT_QUALIFIED_EXPRESSION
import com.pinterest.ktlint.core.ast.ElementType.IMPORT_DIRECTIVE
import com.pinterest.ktlint.core.ast.ElementType.TYPE_REFERENCE
import com.pinterest.ktlint.core.ast.ElementType.WHITE_SPACE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.siblings

/**
 * [See guide](https://github.com/hendraanggrian/rulebook/blob/main/rules.md#type-kotlin-api).
 */
class TypeKotlinApiRule : Rule("type-kotlin-api") {
    internal companion object {
        const val ERROR_MESSAGE = "Replace '%s' with '%s'."
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        // first line of filter
        when (node.elementType) {
            IMPORT_DIRECTIVE -> {
                // get text after `import `
                val importLine = node[WHITE_SPACE].siblings().joinToString("") { it.text }
                val kotlinClassReplacement = importLine.kotlinClassReplacement
                if (kotlinClassReplacement != null) {
                    emit(
                        node[DOT_QUALIFIED_EXPRESSION].startOffset,
                        ERROR_MESSAGE.format(importLine, kotlinClassReplacement),
                        false
                    )
                }
            }
            TYPE_REFERENCE -> {
                // get text without argument and nullability
                val text = node.text.substringBefore('<').substringBefore('?')
                val kotlinClassReplacement = text.kotlinClassReplacement
                if (kotlinClassReplacement != null) {
                    emit(
                        node.startOffset,
                        ERROR_MESSAGE.format(text, kotlinClassReplacement),
                        false
                    )
                }
            }
        }
    }

    private val String.kotlinClassReplacement: String?
        get() {
            if (startsWith("java.lang.") || startsWith("java.util.")) {
                try {
                    val kotlinClassName = Class.forName(this).kotlin.qualifiedName ?: return null
                    return if (kotlinClassName.startsWith("kotlin.")) kotlinClassName else null
                } catch (e: ClassNotFoundException) {
                    return null
                }
            } else if (startsWith("org.junit.")) {
                when (this) {
                    "org.junit.Test" -> return "kotlin.test.Test"
                    "org.junit.Ignore" -> return "kotlin.test.Ignore"
                    "org.junit.Before" -> return "kotlin.test.BeforeTest"
                    "org.junit.After" -> return "kotlin.test.AfterTest"
                }
            }
            return null
        }
}
