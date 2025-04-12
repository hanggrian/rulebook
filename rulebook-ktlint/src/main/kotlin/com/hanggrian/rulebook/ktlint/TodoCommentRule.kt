package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.hanggrian.rulebook.ktlint.internals.siblingsUntil
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EOL_COMMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_SECTION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TEXT
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpace
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import kotlin.text.RegexOption.IGNORE_CASE

/** [See detail](https://hanggrian.github.io/rulebook/rules/#todo-comment) */
public class TodoCommentRule : RulebookRule(ID) {
    override val tokens: TokenSet =
        TokenSet.create(
            EOL_COMMENT,
            KDOC_SECTION,
            KDOC_LEADING_ASTERISK,
        )

    override fun visitToken(node: ASTNode, emit: Emit) {
        // obtain comment content
        val text =
            when (node.elementType) {
                EOL_COMMENT -> node.text
                KDOC_SECTION ->
                    node
                        .takeUnless { KDOC_LEADING_ASTERISK in it }
                        ?.findChildByType(KDOC_TEXT)
                        ?.text
                        ?: return

                else ->
                    node
                        .siblingsUntil(KDOC_LEADING_ASTERISK)
                        .takeUnless { nodes -> nodes.all { it.isWhiteSpace() } }
                        ?.joinToString("") { it.text }
                        ?: return
            }

        // checks for violation
        if (KEYWORD_REGEX.containsMatchIn(text)) {
            emit(
                node.startOffset,
                Messages.get(MSG_KEYWORD, KEYWORD_REGEX.find(text)!!.value),
                false,
            )
        }
        if (!SEPARATOR_REGEX.containsMatchIn(text)) {
            return
        }
        emit(
            node.startOffset,
            Messages.get(MSG_SEPARATOR, SEPARATOR_REGEX.find(text)!!.value.last()),
            false,
        )
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:todo-comment")

        private const val MSG_KEYWORD = "todo.comment.keyword"
        private const val MSG_SEPARATOR = "todo.comment.separator"

        private val KEYWORD_REGEX = Regex("""\b(?i:fixme|todo)(?<!FIXME|TODO)\b""")
        private val SEPARATOR_REGEX = Regex("""\b(todo|fixme)\S""", IGNORE_CASE)
    }
}
