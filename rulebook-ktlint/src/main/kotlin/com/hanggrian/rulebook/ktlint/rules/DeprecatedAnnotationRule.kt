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
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet.create
import org.jetbrains.kotlin.psi.KtImportDirective

/** [See detail](https://hanggrian.github.io/rulebook/rules/#deprecated-annotation) */
public class DeprecatedAnnotationRule : RulebookRule(ID) {
    override val tokens: TokenSet = create(IMPORT_DIRECTIVE, TYPE_REFERENCE)

    override fun isTest(): Boolean = true

    override fun visit(node: ASTNode, emit: Emit) {
        // obtain corresponding qualifier
        val (node2, qualifier) =
            when (node.elementType) {
                IMPORT_DIRECTIVE -> {
                    val dotQualifiedExpression =
                        node.findChildByType(DOT_QUALIFIED_EXPRESSION) ?: return
                    dotQualifiedExpression to (node.psi as KtImportDirective).importPath!!.pathStr
                }

                else -> node to node.text.qualifierName
            }

        // checks for violation
        val replacement = qualifier.kotlinClassReplacement ?: return
        emit(node2.startOffset, Messages[MSG, replacement], false)
    }

    private val String.kotlinClassReplacement
        get() =
            when {
                startsWith("org.junit") -> TEST_ANNOTATIONS_REPLACEMENT[this]
                else -> null
            }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:deprecated-annotation")
        private const val MSG = "deprecated.annotation"

        private val TEST_ANNOTATIONS_REPLACEMENT =
            mapOf(
                "org.junit.Test" to "kotlin.test.Test",
                "org.junit.Ignore" to "kotlin.test.Ignore",
                "org.junit.Before" to "kotlin.test.BeforeTest",
                "org.junit.After" to "kotlin.test.AfterTest",
            )
    }
}
