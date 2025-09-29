package com.hanggrian.rulebook.ktlint

import com.google.common.truth.Truth.assertThat
import com.pinterest.ktlint.rule.engine.core.api.Rule
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode
import org.jetbrains.kotlin.psi.KtFile
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.atMost
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class RulesTest {
    @Mock
    private lateinit var rule: Rule

    @Mock
    private lateinit var fileNode: FileASTNode

    @Mock
    private lateinit var file: KtFile

    @Test
    fun getFileName() {
        `when`(fileNode.psi).thenReturn(file)
        `when`(file.virtualFilePath).thenReturn("package.kt")
        assertThat(rule.getFileName(fileNode)).isNull()

        `when`(fileNode.psi).thenReturn(file)
        `when`(file.virtualFilePath).thenReturn("MyClass.kt")
        assertThat(rule.getFileName(fileNode)).isEqualTo("MyClass")

        verify(fileNode, atMost(2)).psi
        verify(file, atMost(2)).virtualFilePath
    }
}
