package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.siblingsUntil
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_SECTION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpaceWithNewline
import com.pinterest.ktlint.rule.engine.core.api.prevSibling
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#block-tag-initial-line-spacing) */
public class BlockTagInitialLineSpacingRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(KDOC_SECTION)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // only allow first tag
        val kdocTag = node.findChildByType(KDOC_TAG) ?: return

        // checks for violation
        kdocTag
            .prevKdocLeadingAsterisk
            ?.prevKdocLeadingAsterisk
            ?.siblingsUntil(KDOC_LEADING_ASTERISK)
            ?.takeUnless { it.size == 1 && it.single().isWhiteSpaceWithNewline() }
            ?: return
        emit(kdocTag.startOffset, Messages[MSG], false)
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:block-tag-initial-line-spacing")

        const val MSG = "block.tag.initial.line.spacing"

        private val ASTNode.prevKdocLeadingAsterisk: ASTNode?
            get() = prevSibling { it.elementType == KDOC_LEADING_ASTERISK }
    }
}
