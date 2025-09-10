package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.qualifierName
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DOT_QUALIFIED_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IMPORT_DIRECTIVE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.psi.KtImportDirective

/** [See detail](https://hanggrian.github.io/rulebook/rules/#built-in-types) */
public class BuiltInTypesRule : RulebookRule(ID) {
    private var isTestClass = false

    override val tokens: TokenSet = TokenSet.create(IMPORT_DIRECTIVE, TYPE_REFERENCE)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // obtain corresponding qualifier
        val (node2, qualifier) =
            when (node.elementType) {
                IMPORT_DIRECTIVE -> {
                    // get text after `import`
                    val path = (node.psi as KtImportDirective).importPath!!.pathStr

                    // check if running on test
                    if (!isTestClass && TEST_LIBRARIES.any { path.startsWith(it) }) {
                        isTestClass = true
                    }

                    (node.findChildByType(DOT_QUALIFIED_EXPRESSION) ?: return) to
                        path
                }

                else -> node to node.text.qualifierName
            }

        // checks for violation
        val replacement = qualifier.kotlinClassReplacement ?: return
        emit(node2.startOffset, Messages.get(MSG, replacement), false)
    }

    private val String.kotlinClassReplacement
        get() =
            when {
                startsWith("java.lang.") ->
                    try {
                        Class
                            .forName(this)
                            .kotlin
                            .qualifiedName
                            ?.takeIf { it.startsWith("kotlin.") }
                    } catch (e: ClassNotFoundException) {
                        null
                    }

                startsWith("java.util.") -> COLLECTIONS_REPLACEMENT[this]
                isTestClass && startsWith("org.junit") -> TEST_ANNOTATIONS_REPLACEMENT[this]
                else -> null
            }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:built-in-types")

        private const val MSG = "built.in.types"

        private val COLLECTIONS_REPLACEMENT =
            mapOf(
                "java.util.Iterator" to "kotlin.collections.Iterator",
                "java.util.Iterable" to "kotlin.collections.Iterable",
                "java.util.Collection" to "kotlin.collections.Collection",
                "java.util.Set" to "kotlin.collections.Set",
                "java.util.HashSet" to "kotlin.collections.HashSet",
                "java.util.LinkedHashSet" to "kotlin.collections.LinkedHashSet",
                "java.util.List" to "kotlin.collections.List",
                "java.util.ArrayList" to "kotlin.collections.ArrayList",
                "java.util.ListIterator" to "kotlin.collections.ListIterator",
                "java.util.Map" to "kotlin.collections.Map",
                "java.util.Map.Entry" to "kotlin.collections.Map.Entry",
            )

        private val TEST_LIBRARIES =
            setOf(
                "io.kotest",
                "junit.framework",
                "kotlin.test",
                "org.junit",
                "org.testng",
            )

        private val TEST_ANNOTATIONS_REPLACEMENT =
            mapOf(
                "org.junit.Test" to "kotlin.test.Test",
                "org.junit.Ignore" to "kotlin.test.Ignore",
                "org.junit.Before" to "kotlin.test.BeforeTest",
                "org.junit.After" to "kotlin.test.AfterTest",
            )
    }
}
