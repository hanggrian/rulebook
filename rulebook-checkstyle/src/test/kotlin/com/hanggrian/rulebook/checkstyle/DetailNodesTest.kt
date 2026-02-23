package com.hanggrian.rulebook.checkstyle

import com.google.common.truth.Truth.assertThat
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.DetailNode
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ANNOTATION
import com.puppycrawl.tools.checkstyle.api.TokenTypes.COMMENT_CONTENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.COMPILATION_UNIT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_CONTINUE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_PUBLIC
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_RETURN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_THROW
import com.puppycrawl.tools.checkstyle.api.TokenTypes.MODIFIERS
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SINGLE_LINE_COMMENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST
import org.mockito.Mockito.atLeast
import org.mockito.Mockito.atMost
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import kotlin.test.Test

class DetailNodesTest {
    @Test
    fun maxLineNo() {
        val tree = NodeTree(1, 2, 3, 4, 5, 6, 7)
        assertThat(tree.root.maxLineNo).isEqualTo(7)

        tree.verify()
        tree.all().forEach { verify(it).lineNo }
    }

    @Test
    fun minLineNo() {
        val tree = NodeTree(7, 6, 5, 4, 3, 2, 1)
        assertThat(tree.root.minLineNo).isEqualTo(1)

        tree.verify()
        tree.all().forEach { verify(it).lineNo }
    }

    @Test
    fun firstLeaf() {
        val leaf = mock<DetailAST>()
        val parent = mock<DetailAST> { on { firstChild } doReturn leaf }
        val root = mock<DetailAST> { on { firstChild } doReturn parent }
        assertThat(root.firstLeaf()).isEqualTo(leaf)

        sequenceOf(root, parent).forEach { verify(it, atMost(2)).firstChild }
    }

    @Test
    fun lastLeaf() {
        val leaf = mock<DetailAST>()
        val parent = mock<DetailAST> { on { lastChild } doReturn leaf }
        val root = mock<DetailAST> { on { lastChild } doReturn parent }
        assertThat(root.lastLeaf()).isEqualTo(leaf)

        sequenceOf(root, parent).forEach { verify(it, atMost(2)).lastChild }
    }

    @Test
    fun children() {
        val child1 = mock<DetailAST>()
        val child2 = mock<DetailAST> { on { nextSibling } doReturn child1 }
        val root = mock<DetailAST> { on { firstChild } doReturn child2 }
        assertThat(root.children().toList()).containsExactly(child1, child2)

        verify(root, atMost(2)).firstChild
        verify(child1, atMost(2)).nextSibling
    }

    @Test
    fun contains() {
        val node = mock<DetailAST> { on { findFirstToken(COMPILATION_UNIT) } doReturn mock() }
        assertThat(COMPILATION_UNIT in node).isTrue()

        verify(node).findFirstToken(COMPILATION_UNIT)
    }

    @Test
    fun parent() {
        val root = mock<DetailAST> { on { type } doReturn COMPILATION_UNIT }
        val parent = mock<DetailAST> { on { this.parent } doReturn root }
        val leaf = mock<DetailAST> { on { this.parent } doReturn parent }
        assertThat(leaf.parent { it.type == COMPILATION_UNIT }).isEqualTo(root)

        sequenceOf(root, parent).forEach { verify(it).type }
        verify(leaf).parent

        `when`(root.parent).thenReturn(null)
        assertThat(root.parent { it.type == COMPILATION_UNIT }).isNull()

        verify(root).parent
    }

    @Test
    fun nextSibling() {
        val leaf4 = mock<DetailAST> { on { type } doReturn COMPILATION_UNIT }
        val leaf3 = mock<DetailAST> { on { nextSibling } doReturn leaf4 }
        val leaf2 = mock<DetailAST> { on { nextSibling } doReturn leaf3 }
        val leaf1 = mock<DetailAST> { on { nextSibling } doReturn leaf2 }
        assertThat(leaf1.nextSibling { it.type == COMPILATION_UNIT }).isEqualTo(leaf4)

        sequenceOf(leaf1, leaf2, leaf3).forEach { verify(it).nextSibling }
        verify(leaf4).type

        val leafNode1 = mock<DetailNode>()
        val leafNode2 = mock<DetailNode>()
        val rootNode = mock<DetailNode> { on { children } doReturn arrayOf(leafNode1, leafNode2) }
        `when`(leafNode1.parent).thenReturn(rootNode)
        assertThat(leafNode1.nextSibling()).isEqualTo(leafNode2)

        verify(leafNode1).parent
        verify(rootNode).children

        val root = mock<DetailAST> { on { nextSibling } doReturn null }
        assertThat(root.nextSibling { it.type == COMPILATION_UNIT }).isNull()

        verify(root).nextSibling
    }

    @Test
    fun hasModifier() {
        val publicContainer =
            mock<DetailAST> {
                on { findFirstToken(LITERAL_PUBLIC) } doReturn mock<DetailAST>()
            }
        val modifiersContainer =
            mock<DetailAST> {
                on { findFirstToken(MODIFIERS) } doReturn publicContainer
            }
        assertThat(modifiersContainer.hasModifier(LITERAL_PUBLIC)).isTrue()

        verify(modifiersContainer).findFirstToken(MODIFIERS)
        verify(publicContainer).findFirstToken(LITERAL_PUBLIC)
        LITERAL_PUBLIC in verify(publicContainer)
    }

    @Test
    fun hasAnnotation() {
        val leaf2 = mock<DetailAST> { on { text } doReturn "Deprecated" }
        val leaf1 =
            mock<DetailAST> {
                on { type } doReturn ANNOTATION
                on { findFirstToken(IDENT) } doReturn leaf2
            }
        val parent = mock<DetailAST> { on { firstChild } doReturn leaf1 }
        val root = mock<DetailAST> { on { findFirstToken(MODIFIERS) } doReturn parent }
        `when`(root.findFirstToken(MODIFIERS)).thenReturn(parent)
        `when`(parent.firstChild).thenReturn(leaf1)
        assertThat(root.hasAnnotation("Deprecated")).isTrue()

        verify(root).findFirstToken(MODIFIERS)
        verify(parent).firstChild
        verify(leaf1).type
        verify(leaf1).findFirstToken(IDENT)
        verify(leaf2).text
        verify(parent).children()
    }

    @Test
    fun hasJumpStatement() {
        val returnContainer =
            mock<DetailAST> {
                on { findFirstToken(SLIST) } doReturn null
                on { findFirstToken(LITERAL_RETURN) } doReturn mock()
            }
        val throwContainer =
            mock<DetailAST> {
                on { findFirstToken(SLIST) } doReturn null
                on { findFirstToken(LITERAL_RETURN) } doReturn null
                on { findFirstToken(LITERAL_THROW) } doReturn mock()
            }
        val continueContainer =
            mock<DetailAST> {
                on { findFirstToken(SLIST) } doReturn null
                on { findFirstToken(LITERAL_RETURN) } doReturn null
                on { findFirstToken(LITERAL_THROW) } doReturn null
                on { findFirstToken(LITERAL_CONTINUE) } doReturn mock()
            }
        val breakContainer =
            mock<DetailAST> {
                on { findFirstToken(SLIST) } doReturn null
                on { findFirstToken(LITERAL_RETURN) } doReturn null
                on { findFirstToken(LITERAL_THROW) } doReturn null
                on { findFirstToken(LITERAL_CONTINUE) } doReturn null
            }
        assertThat(returnContainer.hasJumpStatement()).isTrue()
        assertThat(throwContainer.hasJumpStatement()).isTrue()
        assertThat(breakContainer.hasJumpStatement(false)).isFalse()
        assertThat(continueContainer.hasJumpStatement()).isTrue()

        verify(returnContainer).findFirstToken(LITERAL_RETURN)
        verify(throwContainer).findFirstToken(LITERAL_THROW)
        verify(continueContainer).findFirstToken(LITERAL_CONTINUE)
        sequenceOf(
            returnContainer,
            throwContainer,
            continueContainer,
        ).forEach { verify(it).findFirstToken(SLIST) }
    }

    @Test
    fun isLeaf() {
        val node = mock<DetailAST> { on { childCount } doReturn 2 }
        assertThat(node.isLeaf()).isFalse()

        verify(node).childCount
    }

    @Test
    fun isMultiline() {
        val tree = NodeTree(1, 2, 3, 4, 5, 6, 7)
        assertThat(tree.root.isMultiline()).isTrue()
        assertThat(tree.leaf1.isMultiline()).isFalse()

        tree.verify()
    }

    @Test
    fun isComment() {
        val comment = mock<DetailAST> { on { type } doReturn SINGLE_LINE_COMMENT }
        assertThat(comment.isComment()).isTrue()

        verify(comment).type
    }

    @Test
    fun isEolCommentEmpty() {
        val slist = mock<DetailAST> { on { type } doReturn SLIST }
        assertThat(slist.isEolCommentEmpty()).isFalse()

        val content =
            mock<DetailAST> {
                on { type } doReturn COMMENT_CONTENT
                on { text } doReturn "\n"
            }
        val comment =
            mock<DetailAST> {
                on { type } doReturn SINGLE_LINE_COMMENT
                on { firstChild } doReturn content
            }
        assertThat(comment.isEolCommentEmpty()).isTrue()

        sequenceOf(comment, content).forEach { verify(it).type }
        verify(comment).firstChild
        verify(content).text
    }

    private class NodeTree(
        rootValue: Int,
        parent1Value: Int,
        parent2Value: Int,
        leaf1Value: Int,
        leaf2Value: Int,
        leaf3Value: Int,
        leaf4Value: Int,
    ) {
        val leaf4 = mock<DetailAST> { on { lineNo } doReturn leaf4Value }
        val leaf3 =
            mock<DetailAST> {
                on { lineNo } doReturn leaf3Value
                on { nextSibling } doReturn leaf4
            }
        val leaf2 = mock<DetailAST> { on { lineNo } doReturn leaf2Value }
        val leaf1 =
            mock<DetailAST> {
                on { lineNo } doReturn leaf1Value
                on { nextSibling } doReturn leaf2
            }

        val parent2 =
            mock<DetailAST> {
                on { lineNo } doReturn parent2Value
                on { childCount } doReturn 2
                on { firstChild } doReturn leaf3
            }
        val parent1 =
            mock<DetailAST> {
                on { lineNo } doReturn parent1Value
                on { childCount } doReturn 2
                on { firstChild } doReturn leaf1
                on { nextSibling } doReturn parent2
            }
        val root =
            mock<DetailAST> {
                on { lineNo } doReturn rootValue
                on { childCount } doReturn 2
                on { firstChild } doReturn parent1
            }

        fun all() = sequenceOf(root, parent1, parent2, leaf1, leaf2, leaf3, leaf4)

        fun verify() =
            all().forEach { node ->
                verify(node, atLeast(1)).run {
                    isLeaf()
                    children()
                }
            }
    }
}
