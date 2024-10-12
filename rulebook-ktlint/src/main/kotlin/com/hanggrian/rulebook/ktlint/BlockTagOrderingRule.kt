package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_SECTION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG_NAME
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#block-tag-ordering)
 */
public class BlockTagOrderingRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(KDOC)

    override fun visitToken(node: ASTNode, emit: Emit) {
        var lastTag: String? = null
        for (child in node
            .children()
            .filter { it.elementType == KDOC_SECTION }
            .mapNotNull { it.findChildByType(KDOC_TAG) }) {
            val currentTag = child.findChildByType(KDOC_TAG_NAME)!!.text

            // checks for violation
            if (MEMBER_POSITIONS.getOrDefault(lastTag, -1) > MEMBER_POSITIONS[currentTag]!!) {
                emit(child.startOffset, Messages.get(MSG, currentTag, lastTag!!), false)
            }

            lastTag = currentTag
        }
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:block-tag-ordering")

        const val MSG = "block.tag.ordering"

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
