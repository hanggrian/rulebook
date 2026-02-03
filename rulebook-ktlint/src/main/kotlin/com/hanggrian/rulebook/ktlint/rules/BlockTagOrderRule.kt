package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_SECTION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG_NAME
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children20
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#block-tag-order) */
public class BlockTagOrderRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(KDOC)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // collect block tags
        val kdocTags =
            node
                .children20
                .filter { it.elementType === KDOC_SECTION }
                .mapNotNull { it.findChildByType(KDOC_TAG) }
                .toList()

        // checks for violation
        for ((i, kdocTag) in kdocTags.withIndex()) {
            val lastKdocTagName =
                kdocTags
                    .getOrNull(i - 1)
                    ?.findChildByType(KDOC_TAG_NAME)
                    ?: continue
            val kdocTagName = kdocTag.findChildByType(KDOC_TAG_NAME) ?: continue
            MEMBER_POSITIONS
                .getOrDefault(lastKdocTagName.text, -1)
                .takeIf { it > MEMBER_POSITIONS[kdocTagName.text]!! }
                ?: continue
            emit(
                kdocTag.startOffset,
                Messages[MSG, kdocTagName.text, lastKdocTagName.text],
                false,
            )
        }
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:block-tag-order")
        private const val MSG = "block.tag.order"

        private val MEMBER_POSITIONS =
            mapOf(
                "@constructor" to 0,
                "@receiver" to 1,
                "@param" to 2,
                "@property" to 3,
                "@return" to 4,
                "@throws" to 5,
                "@see" to 6,
            )
    }
}
