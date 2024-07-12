package com.hanggrian.rulebook.ktlint.internals

import com.pinterest.ktlint.rule.engine.core.api.AutocorrectDecision
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EOL_COMMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IF
import com.pinterest.ktlint.rule.engine.core.api.ElementType.MODIFIER_LIST
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import com.pinterest.ktlint.rule.engine.core.api.Rule
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.psiUtil.children

internal typealias Emit = (
    offset: Int,
    errorMessage: String,
    canBeAutoCorrected: Boolean,
) -> AutocorrectDecision

internal inline val ASTNode.endOffset: Int get() = startOffset + textLength

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

internal fun ASTNode.isWhitespaceMultipleNewline(): Boolean =
    elementType == WHITE_SPACE && text.count { it == '\n' } > 1

internal fun ASTNode.isWhitespaceSingleNewline(): Boolean =
    elementType == WHITE_SPACE && text.count { it == '\n' } == 1

internal fun ASTNode.isEolCommentEmpty(): Boolean =
    elementType == EOL_COMMENT && text.substringAfter("//").isBlank()

internal val ASTNode.lastIf: ASTNode?
    get() {
        for (child in children().asIterable().reversed()) {
            return when (child.elementType) {
                IF -> child
                WHITE_SPACE, RBRACE, EOL_COMMENT -> continue
                else -> null
            }
        }
        return null
    }

/** Returns text without argument and nullability. */
internal val ASTNode.qualifierName: String get() = text.substringBefore('<').substringBefore('?')

/** Returns filename, obtained from `com.pinterest.ktlint.ruleset.standard.FilenameRule`. */
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
