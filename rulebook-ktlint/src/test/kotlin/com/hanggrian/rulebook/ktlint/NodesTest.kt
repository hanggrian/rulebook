package com.hanggrian.rulebook.ktlint

import com.google.common.truth.Truth.assertThat
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK_COMMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BREAK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CONTINUE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DESTRUCTURING_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EOL_COMMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IMPORT_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RETURN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THROW
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.mockito.Mockito.atMost
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import kotlin.test.Test

class NodesTest {
    @Test
    fun endOffset() {
        val node =
            mock<ASTNode> {
                on { startOffset } doReturn 0
                on { textLength } doReturn 10
            }
        assertThat(node.endOffset).isEqualTo(10)

        verify(node).startOffset
        verify(node).textLength
    }

    @Test
    fun siblingsUntil() {
        val third = mock<ASTNode> { on { elementType } doReturn IMPORT_KEYWORD }
        val second = mock<ASTNode> { on { treeNext } doReturn third }
        val first = mock<ASTNode> { on { treeNext } doReturn second }
        assertThat(first.siblingsUntil(IMPORT_KEYWORD).toList()).containsExactly(second)

        sequenceOf(first, second).forEach { verify(it).treeNext }
        verify(third).elementType
    }

    @Test
    fun contains() {
        val node = mock<ASTNode> { on { findChildByType(IMPORT_KEYWORD) } doReturn mock() }
        assertThat(IMPORT_KEYWORD in node).isTrue()

        verify(node).findChildByType(IMPORT_KEYWORD)
    }

    @Test
    fun hasJumpStatement() {
        val returnContainer = mock<ASTNode> { on { findChildByType(RETURN) } doReturn mock() }
        val throwContainer = mock<ASTNode> { on { findChildByType(THROW) } doReturn mock() }
        val breakContainer = mock<ASTNode> { on { findChildByType(BREAK) } doReturn mock() }
        val continueContainer = mock<ASTNode> { on { findChildByType(CONTINUE) } doReturn mock() }
        assertThat(returnContainer.hasJumpStatement()).isTrue()
        assertThat(throwContainer.hasJumpStatement()).isTrue()
        assertThat(breakContainer.hasJumpStatement()).isTrue()
        assertThat(continueContainer.hasJumpStatement()).isTrue()

        verify(returnContainer).findChildByType(RETURN)
        verify(throwContainer).findChildByType(THROW)
        verify(breakContainer).findChildByType(BREAK)
        verify(continueContainer).findChildByType(CONTINUE)
        sequenceOf(
            returnContainer,
            throwContainer,
            breakContainer,
            continueContainer,
        ).forEach { verify(it).findChildByType(BLOCK) }

        val blockContainer =
            mock<ASTNode> { on { findChildByType(BLOCK) } doReturn returnContainer }
        assertThat(blockContainer.hasJumpStatement()).isTrue()

        verify(blockContainer).findChildByType(BLOCK)
        verify(returnContainer, atMost(2)).findChildByType(RETURN)
    }

    @Test
    fun isChildOfProperty() {
        val property = mock<ASTNode> { on { elementType } doReturn PROPERTY }
        val node = mock<ASTNode> { on { treeParent } doReturn property }
        assertThat(node.isChildOfProperty()).isTrue()

        verify(property).elementType
        verify(node).treeParent

        val destructuringDeclaration =
            mock<ASTNode> { on { elementType } doReturn DESTRUCTURING_DECLARATION }
        `when`(node.treeParent).thenReturn(destructuringDeclaration)
        assertThat(node.isChildOfProperty()).isTrue()

        verify(destructuringDeclaration, atMost(2)).elementType
        verify(node, atMost(2)).treeParent
    }

    @Test
    fun isMultiline() {
        val whitespace =
            mock<ASTNode> {
                on { elementType } doReturn WHITE_SPACE
                on { textContains('\n') } doReturn true
            }
        assertThat(whitespace.isMultiline()).isTrue()

        verify(whitespace).elementType
        verify(whitespace).textContains('\n')

        val parent = mock<ASTNode> { on { firstChildNode } doReturn whitespace }
        assertThat(parent.isMultiline()).isTrue()

        verify(whitespace, atMost(2)).elementType
        verify(whitespace, atMost(2)).textContains('\n')
        verify(parent).firstChildNode
    }

    @Test
    fun isComment() {
        val comment = mock<ASTNode> { on { elementType } doReturn EOL_COMMENT }
        val blockComment = mock<ASTNode> { on { elementType } doReturn BLOCK_COMMENT }
        val kdoc = mock<ASTNode> { on { elementType } doReturn KDOC }
        assertThat(comment.isComment()).isTrue()
        assertThat(blockComment.isComment()).isTrue()
        assertThat(kdoc.isComment()).isTrue()

        verify(comment).elementType
        verify(blockComment, atMost(2)).elementType
        verify(kdoc, atMost(3)).elementType
    }

    @Test
    fun isWhitespaceSingleLine() {
        val comment = mock<ASTNode> { on { elementType } doReturn EOL_COMMENT }
        assertThat(comment.isWhitespaceSingleLine()).isFalse()

        verify(comment).elementType

        val whitespace =
            mock<ASTNode> {
                on { elementType } doReturn WHITE_SPACE
                on { text } doReturn "\n"
            }
        assertThat(whitespace.isWhitespaceSingleLine()).isTrue()

        verify(whitespace).elementType
        verify(whitespace).text
    }

    @Test
    fun isWhitespaceMultiline() {
        val comment = mock<ASTNode> { on { elementType } doReturn EOL_COMMENT }
        assertThat(comment.isWhitespaceMultiline()).isFalse()

        val whitespace =
            mock<ASTNode> {
                on { elementType } doReturn WHITE_SPACE
                on { text } doReturn "\n\n"
            }
        assertThat(whitespace.isWhitespaceMultiline()).isTrue()

        verify(whitespace).elementType
        verify(whitespace).text
    }

    @Test
    fun isEolCommentEmpty() {
        val blockComment = mock<ASTNode> { on { elementType } doReturn BLOCK_COMMENT }
        assertThat(blockComment.isEolCommentEmpty()).isFalse()

        val comment =
            mock<ASTNode> {
                on { elementType } doReturn EOL_COMMENT
                on { text } doReturn "// "
            }
        assertThat(comment.isEolCommentEmpty()).isTrue()

        verify(comment).elementType
        verify(comment).text
    }
}
