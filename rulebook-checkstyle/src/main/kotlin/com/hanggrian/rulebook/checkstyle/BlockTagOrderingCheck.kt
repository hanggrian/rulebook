package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailNode
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.JAVADOC
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.JAVADOC_TAG

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#block-tag-ordering)
 */
public class BlockTagOrderingCheck : JavadocCheck() {
    override fun getDefaultJavadocTokens(): IntArray = intArrayOf(JAVADOC)

    override fun visitJavadocToken(node: DetailNode) {
        var lastTag: String? = null
        for (child in node
            .children
            .filter { it.type == JAVADOC_TAG }) {
            val currentTag = child.children.first().text

            // checks for violation
            if (MEMBER_POSITIONS.getOrDefault(lastTag, -1) > MEMBER_POSITIONS[currentTag]!!) {
                log(child.lineNumber, Messages.get(MSG, currentTag, lastTag!!))
            }

            lastTag = currentTag
        }
    }

    internal companion object {
        const val MSG = "block.tag.ordering"

        private val MEMBER_POSITIONS =
            mapOf(
                "@param" to 0,
                "@return" to 1,
                "@throws" to 2,
                "@see" to 3,
            )
    }
}
