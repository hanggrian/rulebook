package com.hanggrian.rulebook.checkstyle

import com.google.common.truth.Truth.assertThat
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.DetailNode
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ANNOTATION
import com.puppycrawl.tools.checkstyle.api.TokenTypes.COMMENT_CONTENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.COMPILATION_UNIT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_BREAK
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_CONTINUE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_PUBLIC
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_RETURN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_THROW
import com.puppycrawl.tools.checkstyle.api.TokenTypes.MODIFIERS
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SINGLE_LINE_COMMENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.atLeast
import org.mockito.Mockito.atMost
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class DetailNodesTest {
    @Mock
    private lateinit var rootAst: DetailAST

    @Mock
    private lateinit var parentAst1: DetailAST

    @Mock
    private lateinit var parentAst2: DetailAST

    @Mock
    private lateinit var leafAst1: DetailAST

    @Mock
    private lateinit var leafAst2: DetailAST

    @Mock
    private lateinit var leafAst3: DetailAST

    @Mock
    private lateinit var leafAst4: DetailAST

    @Mock
    private lateinit var rootNode: DetailNode

    @Mock
    private lateinit var leafNode1: DetailNode

    @Mock
    private lateinit var leafNode2: DetailNode

    @Test
    fun maxLineNo() {
        mockMinMaxLineTree(1, 2, 3, 4, 5, 6, 7)
        assertThat(rootAst.maxLineNo).isEqualTo(7)

        verifyMinMaxLineTree()
    }

    @Test
    fun minLineNo() {
        mockMinMaxLineTree(7, 6, 5, 4, 3, 2, 1)
        assertThat(rootAst.minLineNo).isEqualTo(1)

        verifyMinMaxLineTree()
    }

    @Test
    fun firstLeaf() {
        `when`(rootAst.firstChild).thenReturn(parentAst1)
        `when`(parentAst1.firstChild).thenReturn(leafAst1)
        assertThat(rootAst.firstLeaf()).isEqualTo(leafAst1)

        sequenceOf(rootAst, parentAst1).forEach { verify(it, atMost(2)).firstChild }
    }

    @Test
    fun lastLeaf() {
        `when`(rootAst.lastChild).thenReturn(parentAst2)
        `when`(parentAst2.lastChild).thenReturn(leafAst4)
        assertThat(rootAst.lastLeaf()).isEqualTo(leafAst4)

        sequenceOf(rootAst, parentAst1).forEach { verify(it, atMost(2)).lastChild }
    }

    @Test
    fun children() {
        `when`(rootAst.firstChild).thenReturn(parentAst1)
        `when`(parentAst1.nextSibling).thenReturn(parentAst2)
        assertThat(rootAst.children().toList()).containsExactly(parentAst1, parentAst2)

        verify(rootAst, atMost(2)).firstChild
        verify(parentAst1, atMost(2)).nextSibling
    }

    @Test
    fun contains() {
        `when`(rootAst.findFirstToken(COMPILATION_UNIT)).thenReturn(parentAst1)
        assertThat(COMPILATION_UNIT in rootAst).isTrue()

        verify(rootAst).findFirstToken(COMPILATION_UNIT)
    }

    @Test
    fun parent() {
        `when`(rootAst.type).thenReturn(COMPILATION_UNIT)
        `when`(parentAst1.parent).thenReturn(rootAst)
        `when`(leafAst1.parent).thenReturn(parentAst1)
        assertThat(leafAst1.parent { it.type == COMPILATION_UNIT }).isEqualTo(rootAst)

        sequenceOf(rootAst, parentAst1).forEach { verify(it).type }
        verify(leafAst1).parent
    }

    @Test
    fun nextSibling() {
        `when`(leafAst1.nextSibling).thenReturn(leafAst2)
        `when`(leafAst2.nextSibling).thenReturn(leafAst3)
        `when`(leafAst3.nextSibling).thenReturn(leafAst4)
        `when`(leafAst4.type).thenReturn(COMPILATION_UNIT)
        assertThat(leafAst1.nextSibling { it.type == COMPILATION_UNIT }).isEqualTo(leafAst4)

        sequenceOf(leafAst1, leafAst2, leafAst3).forEach { verify(it).nextSibling }
        verify(leafAst4).type

        `when`(leafNode1.parent).thenReturn(rootNode)
        `when`(rootNode.children).thenReturn(arrayOf(leafNode1, leafNode2))
        assertThat(leafNode1.nextSibling()).isEqualTo(leafNode2)

        verify(leafNode1).parent
        verify(rootNode).children
    }

    @Test
    fun hasModifier() {
        `when`(rootAst.findFirstToken(MODIFIERS)).thenReturn(parentAst1)
        `when`(parentAst1.findFirstToken(LITERAL_PUBLIC)).thenReturn(leafAst1)
        assertThat(rootAst.hasModifier(LITERAL_PUBLIC)).isTrue()

        verify(rootAst).findFirstToken(MODIFIERS)
        verify(parentAst1).findFirstToken(LITERAL_PUBLIC)
        LITERAL_PUBLIC in verify(parentAst1)
    }

    @Test
    fun hasAnnotation() {
        `when`(rootAst.findFirstToken(MODIFIERS)).thenReturn(parentAst1)
        `when`(parentAst1.firstChild).thenReturn(leafAst1)

        `when`(leafAst1.type).thenReturn(ANNOTATION)
        `when`(leafAst1.findFirstToken(IDENT)).thenReturn(leafAst2)
        `when`(leafAst2.text).thenReturn("Deprecated")

        assertThat(rootAst.hasAnnotation("Deprecated")).isTrue()

        verify(rootAst).findFirstToken(MODIFIERS)
        verify(parentAst1).firstChild
        verify(leafAst1).type
        verify(leafAst1).findFirstToken(IDENT)
        verify(leafAst2).text
        verify(parentAst1).children()
    }

    @Test
    fun hasJumpStatement() {
        `when`(rootAst.findFirstToken(SLIST)).thenReturn(null)
        `when`(rootAst.findFirstToken(LITERAL_RETURN)).thenReturn(null)
        `when`(rootAst.findFirstToken(LITERAL_THROW)).thenReturn(null)
        `when`(rootAst.findFirstToken(LITERAL_CONTINUE)).thenReturn(null)
        `when`(rootAst.findFirstToken(LITERAL_BREAK)).thenReturn(parentAst1)
        assertThat(rootAst.hasJumpStatement()).isTrue()

        `when`(rootAst.findFirstToken(SLIST)).thenReturn(parentAst2)
        `when`(parentAst2.findFirstToken(LITERAL_RETURN)).thenReturn(leafAst2)
        assertThat(rootAst.hasJumpStatement(false)).isTrue()

        verify(rootAst, atMost(2)).findFirstToken(SLIST)
        verify(rootAst).findFirstToken(LITERAL_RETURN)
        verify(rootAst).findFirstToken(LITERAL_THROW)
        verify(rootAst).findFirstToken(LITERAL_CONTINUE)
        verify(rootAst).findFirstToken(LITERAL_BREAK)
        verify(parentAst2).findFirstToken(LITERAL_RETURN)
        LITERAL_BREAK in verify(rootAst)
        LITERAL_RETURN in verify(rootAst)
    }

    @Test
    fun isLeaf() {
        `when`(rootAst.childCount).thenReturn(2)
        assertThat(rootAst.isLeaf()).isFalse()

        verify(rootAst).childCount
    }

    @Test
    fun isMultiline() {
        mockMinMaxLineTree(1, 2, 3, 4, 5, 6, 7)
        assertThat(rootAst.isMultiline()).isTrue()
        assertThat(leafAst1.isMultiline()).isFalse()

        verifyMinMaxLineTree()
    }

    @Test
    fun isComment() {
        `when`(rootAst.type).thenReturn(SINGLE_LINE_COMMENT)
        assertThat(rootAst.isComment()).isTrue()

        verify(rootAst).type
    }

    @Test
    fun isEolCommentEmpty() {
        `when`(rootAst.type).thenReturn(SINGLE_LINE_COMMENT)
        `when`(rootAst.firstChild).thenReturn(parentAst1)
        `when`(parentAst1.type).thenReturn(COMMENT_CONTENT)
        `when`(parentAst1.text).thenReturn("\n")
        assertThat(rootAst.isEolCommentEmpty()).isTrue()

        sequenceOf(rootAst, parentAst1).forEach { verify(it).type }
        verify(rootAst).firstChild
        verify(parentAst1).text
    }

    private fun mockMinMaxLineTree(
        rootValue: Int,
        parent1Value: Int,
        parent2Value: Int,
        leaf1Value: Int,
        leaf2Value: Int,
        leaf3Value: Int,
        leaf4Value: Int,
    ) {
        `when`(rootAst.childCount).thenReturn(2)
        `when`(parentAst1.childCount).thenReturn(2)
        `when`(parentAst2.childCount).thenReturn(2)

        `when`(rootAst.firstChild).thenReturn(parentAst1)
        `when`(parentAst1.firstChild).thenReturn(leafAst1)
        `when`(parentAst2.firstChild).thenReturn(leafAst3)

        `when`(parentAst1.nextSibling).thenReturn(parentAst2)
        `when`(leafAst1.nextSibling).thenReturn(leafAst2)
        `when`(leafAst3.nextSibling).thenReturn(leafAst4)

        `when`(rootAst.lineNo).thenReturn(rootValue)
        `when`(parentAst1.lineNo).thenReturn(parent1Value)
        `when`(parentAst2.lineNo).thenReturn(parent2Value)
        `when`(leafAst1.lineNo).thenReturn(leaf1Value)
        `when`(leafAst2.lineNo).thenReturn(leaf2Value)
        `when`(leafAst3.lineNo).thenReturn(leaf3Value)
        `when`(leafAst4.lineNo).thenReturn(leaf4Value)
    }

    private fun verifyMinMaxLineTree() =
        sequenceOf(
            rootAst,
            parentAst1,
            parentAst2,
            leafAst1,
            leafAst2,
            leafAst3,
            leafAst4,
        ).forEach { node ->
            verify(node, atLeast(1)).let {
                it.isLeaf()
                it.children()
            }
        }
}
