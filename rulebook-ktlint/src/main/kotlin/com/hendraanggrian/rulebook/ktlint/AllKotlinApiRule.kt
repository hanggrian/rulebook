package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.rule.engine.core.api.ElementType.DOT_QUALIFIED_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IMPORT_DIRECTIVE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.siblings

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/AllKotlinApi).
 */
class AllKotlinApiRule : RulebookRule("all-kotlin-api") {
    internal companion object {
        const val MSG_CALL = "all.kotlin.api.call"
        const val MSG_TYPE = "all.kotlin.api.type"

        private val JUNIT_REPLACEMENTS = mapOf(
            "org.junit.Test" to "kotlin.test.Test",
            "org.junit.Ignore" to "kotlin.test.Ignore",
            "org.junit.Before" to "kotlin.test.BeforeTest",
            "org.junit.After" to "kotlin.test.AfterTest"
        )
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
                        Messages.get(MSG_CALL, importLine, kotlinClassReplacement),
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
                        Messages.get(MSG_TYPE, text, kotlinClassReplacement),
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
                val replacement = JUNIT_REPLACEMENTS[this]
                if (replacement != null) {
                    return replacement
                }
            }
            return null
        }
}
