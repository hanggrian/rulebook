package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.getFileName
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#class-name-acronym) */
public class ClassNameAcronymRule : RulebookRule(ID) {
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
        emit(node2.startOffset, Messages.get(MSG, transformation), false)
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:class-name-acronym")

        const val MSG = "class.name.acronym"

        private val ABBREVIATION_REGEX = Regex("[A-Z]{3,}")
    }
}
