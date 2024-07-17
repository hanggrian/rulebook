package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.getFileName
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#class-name-acronym-capitalization)
 */
public class ClassNameAcronymCapitalizationRule : Rule("class-name-acronym-capitalization") {
    override val tokens: TokenSet = TokenSet.create(CLASS, OBJECT_DECLARATION, FILE)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // obtain class name
        val (node2, name) =
            when (node.elementType) {
                CLASS, OBJECT_DECLARATION ->
                    node
                        .findChildByType(IDENTIFIER)
                        ?.takeIf { ABBREVIATION_REGEX.containsMatchIn(it.text) }
                        ?.let { it to it.text }
                else ->
                    getFileName(node)
                        ?.takeIf { ABBREVIATION_REGEX.containsMatchIn(it) }
                        ?.let { node to it }
            } ?: return

        // checks for violation
        val transformation =
            name
                .takeIf { ABBREVIATION_REGEX.containsMatchIn(it) }
                ?.let { n ->
                    ABBREVIATION_REGEX.replace(n) {
                        it.value.first() +
                            when {
                                it.range.last == n.lastIndex -> it.value.drop(1).lowercase()
                                else ->
                                    it.value.drop(1).dropLast(1).lowercase() +
                                        it.value.last()
                            }
                    }
                } ?: return
        emit(node2.startOffset, Messages.get(MSG, transformation), false)
    }

    internal companion object {
        const val MSG = "class.name.acronym.capitalization"

        private val ABBREVIATION_REGEX = Regex("[A-Z]{3,}")
    }
}
