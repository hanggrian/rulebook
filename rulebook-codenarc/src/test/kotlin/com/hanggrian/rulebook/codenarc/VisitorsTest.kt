package com.hanggrian.rulebook.codenarc

import com.google.common.truth.Truth.assertThat
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codenarc.rule.AbstractAstVisitor
import org.codenarc.source.SourceCode
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.atMost
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class VisitorsTest {
    @Mock
    private lateinit var visitor: AbstractAstVisitor

    @Mock
    private lateinit var code: SourceCode

    @Test
    fun getLineNumberBefore() {
        val node1 = mock<ASTNode> { on { lastLineNumber } doReturn 1 }
        val node2 = mock<ASTNode> { on { lineNumber } doReturn 2 }
        `when`(code.line(1)).thenReturn("int i = 0")
        `when`(visitor.sourceCode).thenReturn(code)
        assertThat(visitor.getLineNumberBefore(node2, node1)).isEqualTo(1)

        verify(node1).lastLineNumber
        verify(node2, atMost(2)).lineNumber
        verify(code).line(1)
        verify(visitor).sourceCode
    }

    @Test
    fun hasCommentAbove() {
        val node = mock<ASTNode> { on { lineNumber } doReturn 1 }
        `when`(node.lineNumber).thenReturn(2)
        `when`(code.line(1)).thenReturn("// comment")
        `when`(visitor.sourceCode).thenReturn(code)
        assertThat(visitor.hasCommentAbove(node)).isTrue()

        verify(node).lineNumber
        verify(code).line(1)
        verify(visitor).sourceCode
    }

    @Test
    fun getLiteral() {
        val constantExpression =
            mock<ConstantExpression> {
                on { lastColumnNumber } doReturn 11
                on { lineNumber } doReturn 1
            }
        `when`(code.lines).thenReturn(listOf("int l = 0L"))
        `when`(visitor.sourceCode).thenReturn(code)
        assertThat(visitor.getLiteral(constantExpression)).isEqualTo("L")

        verify(constantExpression, atMost(3)).lastColumnNumber
        verify(constantExpression).lineNumber
        verify(code).lines
        verify(visitor).sourceCode
    }

    @Test
    fun sourceLineNullable() {
        val node1 = mock<ASTNode> { on { lineNumber } doReturn -1 }
        assertThat(visitor.sourceLineNullable(node1)).isNull()

        val node2 = mock<ASTNode> { on { lineNumber } doReturn 1 }
        `when`(code.lines).thenReturn(listOf("int i = 0", "int j = 1"))
        `when`(visitor.sourceCode).thenReturn(code)
        assertThat(visitor.sourceLineNullable(node2)).isNotNull()

        verify(node1).lineNumber
        verify(node2, atMost(2)).lineNumber
        verify(code).lines
        verify(visitor, atMost(2)).sourceCode
    }

    @Test
    fun lastSourceLineNullable() {
        val node1 = mock<ASTNode> { on { lastLineNumber } doReturn -1 }
        assertThat(visitor.lastSourceLineNullable(node1)).isNull()

        val node2 = mock<ASTNode> { on { lastLineNumber } doReturn 1 }
        `when`(code.lines).thenReturn(listOf("int i = 0", "int j = 1"))
        `when`(visitor.sourceCode).thenReturn(code)
        assertThat(visitor.lastSourceLineNullable(node2)).isNotNull()

        verify(node1).lastLineNumber
        verify(node2, atMost(2)).lastLineNumber
        verify(code).lines
        verify(visitor, atMost(2)).sourceCode
    }
}
