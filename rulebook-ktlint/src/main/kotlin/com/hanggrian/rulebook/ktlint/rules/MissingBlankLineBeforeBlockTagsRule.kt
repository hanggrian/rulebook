package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.siblingsUntil
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_SECTION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpaceWithNewline20
import com.pinterest.ktlint.rule.engine.core.api.prevSibling
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#missing-blank-line-before-block-tags) */
public class MissingBlankLineBeforeBlockTagsRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(KDOC_SECTION)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // only allow first tag
        val kdocTag =
            node
                .findChildByType(KDOC_TAG)
                ?: return

        // checks for violation
        kdocTag
            .prevKdocLeadingAsterisk
            ?.prevKdocLeadingAsterisk
            ?.siblingsUntil(KDOC_LEADING_ASTERISK)
            ?.takeUnless { it.singleOrNull().isWhiteSpaceWithNewline20 }
            ?: return
        emit(kdocTag.startOffset, Messages[MSG], false)
    }

    public companion object {
        public val ID: RuleId =
            RuleId("${RulebookRuleSet.ID.value}:missing-blank-line-before-block-tags")

        private const val MSG = "missing.blank.line.before.block.tags"

        private val ASTNode.prevKdocLeadingAsterisk
            get() = prevSibling { it.elementType === KDOC_LEADING_ASTERISK }
    }
}
