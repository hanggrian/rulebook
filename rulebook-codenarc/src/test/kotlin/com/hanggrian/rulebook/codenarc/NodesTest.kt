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
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codehaus.groovy.ast.stmt.Statement
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
    private lateinit var classNode: ClassNode

    @Mock
    private lateinit var annotationNode: AnnotationNode

    @Mock
    private lateinit var annotatedNode: AnnotatedNode

    @Mock
    private lateinit var blockStatement: BlockStatement

    @Mock
    private lateinit var ifStatement: IfStatement

    @Mock
    private lateinit var caseStatement: CaseStatement

    @Mock
    private lateinit var tupleExpression: TupleExpression

    @Mock
    private lateinit var statement1: Statement

    @Mock
    private lateinit var statement2: Statement

    @Test
    fun hasAnnotation() {
        `when`(classNode.name).thenReturn("Override")
        `when`(annotationNode.classNode).thenReturn(classNode)
        `when`(annotatedNode.annotations).thenReturn(listOf(annotationNode))
        assertThat(annotatedNode.hasAnnotation("Override")).isTrue()

        verify(classNode).name
        verify(annotationNode).classNode
        verify(annotatedNode).annotations
    }

    @Test
    fun firstStatement() {
        `when`(blockStatement.statements).thenReturn(listOf(BreakStatement()))
        assertThat(blockStatement.firstStatement())
            .isInstanceOf(BreakStatement::class.java)

        verify(blockStatement).statements
    }

    @Test
    fun lastExpression() {
        `when`(tupleExpression.expressions).thenReturn(listOf(ArgumentListExpression()))
        assertThat(tupleExpression.lastExpression())
            .isInstanceOf(ArgumentListExpression::class.java)

        verify(tupleExpression).expressions
    }

    @Test
    fun hasJumpStatement() {
        `when`(blockStatement.statements).thenReturn(listOf(BreakStatement()))
        assertThat(blockStatement.hasJumpStatement()).isTrue()
        assertThat(blockStatement.hasJumpStatement(false)).isFalse()

        verify(blockStatement, atMost(2)).statements
    }

    @Test
    fun isMultiline() {
        `when`(ifStatement.elseBlock).thenReturn(blockStatement)
        assertThat(ifStatement.isMultiline()).isFalse()

        `when`(caseStatement.code).thenReturn(blockStatement)
        assertThat(caseStatement.isMultiline()).isFalse()

        `when`(statement1.lineNumber).thenReturn(1)
        `when`(statement2.lastLineNumber).thenReturn(2)
        `when`(blockStatement.statements).thenReturn(listOf(statement1, statement2))
        assertThat(blockStatement.isMultiline()).isTrue()

        verify(blockStatement, atMost(5)).statements
    }
}
