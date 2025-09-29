package com.hanggrian.rulebook.ktlint

import com.google.common.truth.Truth.assertThat
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EOL_COMMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IMPORT_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RETURN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.atMost
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class NodesTest {
    @Mock
    private lateinit var node1: ASTNode

    @Mock
    private lateinit var node2: ASTNode

    @Mock
    private lateinit var node3: ASTNode

    @Test
    fun endOffset() {
        `when`(node1.startOffset).thenReturn(0)
        `when`(node1.textLength).thenReturn(10)
        assertThat(node1.endOffset).isEqualTo(10)

        verify(node1).startOffset
        verify(node1).textLength
    }

    @Test
    fun lastLeaf() {
        `when`(node1.lastChildNode).thenReturn(node2)
        `when`(node2.lastChildNode).thenReturn(node3)
        assertThat(node1.lastLeaf()).isEqualTo(node3)

        sequenceOf(node1, node2).forEach { verify(it, atMost(2)).lastChildNode }
    }

    @Test
    fun siblingsUntil() {
        `when`(node1.treeNext).thenReturn(node2)
        `when`(node2.treeNext).thenReturn(node3)
        `when`(node3.elementType).thenReturn(IMPORT_KEYWORD)
        assertThat(node1.siblingsUntil(IMPORT_KEYWORD).toList()).containsExactly(node2)

        sequenceOf(node1, node2).forEach { verify(it).treeNext }
        verify(node3).elementType
    }

    @Test
    fun contains() {
        `when`(node1.findChildByType(IMPORT_KEYWORD)).thenReturn(node2)
        assertThat(IMPORT_KEYWORD in node1).isTrue()

        verify(node1).findChildByType(IMPORT_KEYWORD)
    }

    @Test
    fun hasJumpStatement() {
        `when`(node1.findChildByType(BLOCK)).thenReturn(null)
        `when`(node1.findChildByType(RETURN)).thenReturn(node2)
        assertThat(node1.hasJumpStatement()).isTrue()

        `when`(node1.findChildByType(BLOCK)).thenReturn(node2)
        `when`(node2.findChildByType(RETURN)).thenReturn(node3)
        assertThat(node1.hasJumpStatement()).isTrue()

        verify(node1, atMost(2)).findChildByType(BLOCK)
        sequenceOf(node1, node2).forEach { verify(it).findChildByType(RETURN) }
    }

    @Test
    fun isMultiline() {
        `when`(node1.elementType).thenReturn(WHITE_SPACE)
        `when`(node1.textContains('\n')).thenReturn(true)
        assertThat(node1.isMultiline()).isTrue()

        `when`(node2.firstChildNode).thenReturn(node3)
        `when`(node3.elementType).thenReturn(WHITE_SPACE)
        `when`(node3.textContains('\n')).thenReturn(true)
        assertThat(node2.isMultiline()).isTrue()

        sequenceOf(node1, node3).forEach {
            verify(it).elementType
            verify(it).textContains('\n')
        }
        verify(node2).firstChildNode
    }

    @Test
    fun isComment() {
        `when`(node1.elementType).thenReturn(EOL_COMMENT)
        assertThat(node1.isComment()).isTrue()

        verify(node1).elementType
    }

    @Test
    fun isWhitespaceSingleLine() {
        `when`(node1.elementType).thenReturn(WHITE_SPACE)
        `when`(node1.text).thenReturn("\n")
        assertThat(node1.isWhitespaceSingleLine()).isTrue()

        verify(node1).elementType
        verify(node1).text
    }

    @Test
    fun isWhitespaceMultiline() {
        `when`(node1.elementType).thenReturn(WHITE_SPACE)
        `when`(node1.text).thenReturn("\n\n")
        assertThat(node1.isWhitespaceMultiline()).isTrue()

        verify(node1).elementType
        verify(node1).text
    }

    @Test
    fun isEolCommentEmpty() {
        `when`(node1.elementType).thenReturn(EOL_COMMENT)
        `when`(node1.text).thenReturn("// ")
        assertThat(node1.isEolCommentEmpty()).isTrue()

        verify(node1).elementType
        verify(node1).text
    }
}
