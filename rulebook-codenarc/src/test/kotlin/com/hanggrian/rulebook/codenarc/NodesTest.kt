package com.hanggrian.rulebook.codenarc

import com.google.common.truth.Truth.assertThat
import org.codehaus.groovy.ast.AnnotatedNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.TupleExpression
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.BreakStatement
import org.codehaus.groovy.ast.stmt.CaseStatement
import org.codehaus.groovy.ast.stmt.ContinueStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codehaus.groovy.ast.stmt.ReturnStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.ThrowStatement
import org.mockito.Mockito.atMost
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import kotlin.test.Test

class NodesTest {
    @Test
    fun hasAnnotation() {
        val `class` = mock<ClassNode> { on { name } doReturn "Override" }
        val annotation = mock<AnnotationNode> { on { classNode } doReturn `class` }
        val annotated = mock<AnnotatedNode> { on { annotations } doReturn listOf(annotation) }
        assertThat(annotated.hasAnnotation("Override")).isTrue()

        verify(`class`).name
        verify(annotation).classNode
        verify(annotated).annotations
    }

    @Test
    fun firstStatement() {
        val block = mock<BlockStatement> { on { statements } doReturn null }
        assertThat(block.firstStatement()).isInstanceOf(BlockStatement::class.java)

        verify(block).statements

        `when`(block.statements).thenReturn(listOf(mock<BreakStatement>()))
        assertThat(block.firstStatement()).isInstanceOf(BreakStatement::class.java)

        verify(block, atMost(2)).statements
    }

    @Test
    fun lastExpression() {
        val tuple = mock<TupleExpression> { on { expressions } doReturn null }
        assertThat(tuple.lastExpression()).isInstanceOf(TupleExpression::class.java)

        verify(tuple).expressions

        `when`(tuple.expressions).thenReturn(listOf(mock<ArgumentListExpression>()))
        assertThat(tuple.lastExpression()).isInstanceOf(ArgumentListExpression::class.java)

        verify(tuple, atMost(2)).expressions
    }

    @Test
    fun hasJumpStatement() {
        val `return` = mock<ReturnStatement>()
        assertThat(`return`.hasJumpStatement()).isTrue()
        assertThat(mock<ThrowStatement>().hasJumpStatement()).isTrue()
        assertThat(mock<ContinueStatement>().hasJumpStatement()).isTrue()
        assertThat(mock<BreakStatement>().hasJumpStatement(false)).isFalse()

        val block = mock<BlockStatement> { on { statements } doReturn listOf(`return`) }
        assertThat(block.hasJumpStatement()).isTrue()

        verify(block).statements
    }

    @Test
    fun isMultiline() {
        val `if` = mock<IfStatement> { on { elseBlock } doReturn mock<BlockStatement>() }
        assertThat(`if`.isMultiline()).isFalse()

        val case = mock<CaseStatement> { on { code } doReturn mock<BlockStatement>() }
        assertThat(case.isMultiline()).isFalse()

        val statement1 = mock<Statement> { on { lineNumber } doReturn 1 }
        val statement2 = mock<Statement> { on { lastLineNumber } doReturn 2 }
        val blockStatement =
            mock<BlockStatement> { on { statements } doReturn listOf(statement1, statement2) }
        assertThat(blockStatement.isMultiline()).isTrue()

        verify(blockStatement, atMost(5)).statements
    }
}
