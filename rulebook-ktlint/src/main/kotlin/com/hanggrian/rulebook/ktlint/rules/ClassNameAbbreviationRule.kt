package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.getFileName
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#class-name-abbreviation) */
public class ClassNameAbbreviationRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(CLASS, OBJECT_DECLARATION, FILE)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // obtain class name
        val (node2, name) =
            when (node.elementType) {
                CLASS, OBJECT_DECLARATION -> {
                    val identifier =
                        node
                            .findChildByType(IDENTIFIER)
                            ?.takeIf { ABBREVIATION_REGEX.containsMatchIn(it.text) }
                            ?: return
                    identifier to identifier.text
                }

                else ->
                    node to
                        (
                            getFileName(node)
                                ?.takeIf { ABBREVIATION_REGEX.containsMatchIn(it) }
                                ?: return
                        )
            }

        // checks for violation
        val transformation =
            name
                .takeIf { ABBREVIATION_REGEX.containsMatchIn(it) }
                ?.let { s ->
                    ABBREVIATION_REGEX.replace(s) {
                        it.value.first() +
                            when {
                                it.range.last == s.lastIndex -> it.value.drop(1).lowercase()

                                else ->
                                    it.value.drop(1).dropLast(1).lowercase() +
                                        it.value.last()
                            }
                    }
                } ?: return
        emit(node2.startOffset, Messages[MSG, transformation], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:class-name-abbreviation")

        private const val MSG = "class.name.abbreviation"

        private val ABBREVIATION_REGEX = Regex("[A-Z]{3,}")
    }
}
