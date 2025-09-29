package com.hanggrian.rulebook.codenarc

import com.google.common.truth.Truth.assertThat
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codenarc.rule.AbstractAstVisitor
import org.codenarc.source.SourceCode
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.atMost
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class VisitorsTest {
    @Mock
    private lateinit var visitor: AbstractAstVisitor

    @Mock
    private lateinit var code: SourceCode

    @Mock
    private lateinit var node1: ASTNode

    @Mock
    private lateinit var node2: ASTNode

    @Mock
    private lateinit var constantExpression: ConstantExpression

    @Test
    fun getLineNumberBefore() {
        `when`(node1.lastLineNumber).thenReturn(1)
        `when`(node2.lineNumber).thenReturn(2)
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
        `when`(node1.lineNumber).thenReturn(2)
        `when`(code.line(1)).thenReturn("// comment")
        `when`(visitor.sourceCode).thenReturn(code)
        assertThat(visitor.hasCommentAbove(node1)).isTrue()

        verify(node1).lineNumber
        verify(code).line(1)
        verify(visitor).sourceCode
    }

    @Test
    fun getLiteral() {
        `when`(constantExpression.lastColumnNumber).thenReturn(11)
        `when`(constantExpression.lineNumber).thenReturn(1)
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
        `when`(node1.lineNumber).thenReturn(-1)
        assertThat(visitor.sourceLineNullable(node1)).isNull()

        `when`(node2.lineNumber).thenReturn(1)
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
        `when`(node1.lastLineNumber).thenReturn(-1)
        assertThat(visitor.lastSourceLineNullable(node1)).isNull()

        `when`(node2.lastLineNumber).thenReturn(1)
        `when`(code.lines).thenReturn(listOf("int i = 0", "int j = 1"))
        `when`(visitor.sourceCode).thenReturn(code)
        assertThat(visitor.lastSourceLineNullable(node2)).isNotNull()

        verify(node1).lastLineNumber
        verify(node2, atMost(2)).lastLineNumber
        verify(code).lines
        verify(visitor, atMost(2)).sourceCode
    }
}
