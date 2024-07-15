package com.hanggrian.rulebook.ktlint.internals

import com.pinterest.ktlint.rule.engine.core.api.AutocorrectDecision
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EOL_COMMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IF
import com.pinterest.ktlint.rule.engine.core.api.ElementType.MODIFIER_LIST
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RETURN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THEN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THROW
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import com.pinterest.ktlint.rule.engine.core.api.Rule
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType
import org.jetbrains.kotlin.psi.KtFile

internal typealias Emit = (
    offset: Int,
    errorMessage: String,
    canBeAutoCorrected: Boolean,
) -> AutocorrectDecision

internal val ASTNode.endOffset: Int get() = startOffset + textLength

internal val ASTNode.qualifierName: String get() = text.substringBefore('<').substringBefore('?')

internal operator fun ASTNode.contains(type: IElementType): Boolean = findChildByType(type) != null

internal fun ASTNode.siblingsUntil(type: IElementType): List<ASTNode> {
    val list = mutableListOf<ASTNode>()
    var next = treeNext
    while (next != null && next.elementType != type) {
        list += next
        next = next.treeNext
    }
    return list
}

internal fun ASTNode.hasModifier(type: IElementType): Boolean =
    MODIFIER_LIST in this && type in findChildByType(MODIFIER_LIST)!!

internal fun ASTNode.hasReturnOrThrow(): Boolean {
    var statements =
        when (elementType) {
            IF -> findChildByType(THEN)!!
            WHEN_ENTRY -> this
            else -> return false
        }
    if (BLOCK in statements) {
        statements = statements.findChildByType(BLOCK)!!
    }
    return RETURN in statements || THROW in statements
}

internal fun ASTNode.isWhitespaceMultipleNewline(): Boolean =
    elementType == WHITE_SPACE && text.count { it == '\n' } > 1

internal fun ASTNode.isWhitespaceSingleNewline(): Boolean =
    elementType == WHITE_SPACE && text.count { it == '\n' } == 1

internal fun ASTNode.isEolCommentEmpty(): Boolean =
    elementType == EOL_COMMENT && text.substringAfter("//").isBlank()

/**
 * @see `com.pinterest.ktlint.ruleset.standard.FilenameRule`.
 */
internal fun Rule.getFileName(node: ASTNode): String? {
    node as FileASTNode?
        ?: error("node is not ${FileASTNode::class} but ${node::class}")
    val filePath = (node.psi as? KtFile)?.virtualFilePath
    if (filePath?.endsWith(".kt") != true || filePath.endsWith("package.kt")) {
        // ignore all non ".kt" files (including ".kts")
        stopTraversalOfAST()
        return null
    }
    return filePath
        .replace('\\', '/') // ensure compatibility with Windows OS
        .substringAfterLast("/")
        .substringBefore(".")
}
