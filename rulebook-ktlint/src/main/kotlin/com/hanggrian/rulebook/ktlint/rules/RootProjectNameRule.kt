package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BINARY_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DOT_QUALIFIED_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LITERAL_STRING_TEMPLATE_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REGULAR_STRING_PART
import com.pinterest.ktlint.rule.engine.core.api.ElementType.SCRIPT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.SCRIPT_INITIALIZER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.STRING_TEMPLATE
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children20
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet.create
import java.io.File
import java.io.File.separator

/** [See detail](https://hanggrian.github.io/rulebook/rules/#root-project-name) */
public class RootProjectNameRule : RulebookRule(ID) {
    override val tokens: TokenSet = create(SCRIPT)

    override fun isScript(): Boolean = true

    override fun visitToken(node: ASTNode, emit: Emit) {
        // only target settings.gradle.kts
        node
            .psi
            .containingFile
            .takeIf { it.name.substringAfterLast(separator) == "settings.gradle.kts" }
            ?: return

        // find root project name assignment
        val binaryExpression =
            node
                .findChildByType(BLOCK)
                ?.children20
                ?.filter { it.elementType === SCRIPT_INITIALIZER }
                ?.mapNotNull { it.findChildByType(BINARY_EXPRESSION) }
                ?.firstOrNull { n ->
                    val referenceExpressions =
                        n
                            .findChildByType(DOT_QUALIFIED_EXPRESSION)
                            ?.children20
                            ?.filter { it.elementType === REFERENCE_EXPRESSION }
                            ?.toList()
                            ?: return@firstOrNull false
                    if (referenceExpressions.size != 2) {
                        return@firstOrNull false
                    }
                    referenceExpressions
                        .first()
                        .findChildByType(IDENTIFIER)
                        ?.text == "rootProject" &&
                        referenceExpressions
                            .last()
                            .findChildByType(IDENTIFIER)
                            ?.text == "name"
                }

        // checks for violation
        if (binaryExpression == null) {
            emit(node.startOffset, Messages[MSG_DEFAULT], false)
            return
        }
        val regularStringPart =
            binaryExpression
                .findChildByType(STRING_TEMPLATE)
                ?.findChildByType(LITERAL_STRING_TEMPLATE_ENTRY)
                ?.findChildByType(REGULAR_STRING_PART)
                ?.takeIf { n -> n.text.any { it in BANNED_CHARACTERS } }
                ?: return
        emit(regularStringPart.startOffset, Messages[MSG_SPECIAL], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:root-project-name")
        private const val MSG_DEFAULT = "root.project.name.default"
        private const val MSG_SPECIAL = "root.project.name.special"

        private val BANNED_CHARACTERS =
            hashSetOf(
                ' ',
                ':',
                '/',
                '\\',
                // Gradle
                '[',
                ']',
                '<',
                '>',
                '"',
                '?',
                '*',
                '|',
                // Shell
                '$',
                '(',
                ')',
                '{',
                '}',
                '&',
                '!',
                ';',
                '\'',
                '`',
            )
    }
}
