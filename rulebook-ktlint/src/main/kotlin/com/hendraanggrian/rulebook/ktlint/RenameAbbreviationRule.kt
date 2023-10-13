package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC
import com.pinterest.ktlint.rule.engine.core.api.ElementType.MODIFIER_LIST
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.psiUtil.children
import java.nio.file.Paths

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/RenameAbbreviation).
 */
class RenameAbbreviationRule : RulebookRule("rename-abbreviation") {
    internal companion object {
        const val MSG_FILE = "rename.abbreviation.file"
        const val MSG_OTHERS = "rename.abbreviation.others"
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        // first line of filter
        when (node.elementType) {
            PROPERTY -> {
                // allow all uppercase, which usually is static property
                val identifier = node.findChildByType(IDENTIFIER) ?: return
                if (identifier.text.isStaticPropertyName()) {
                    return
                }

                // check for violation
                if (identifier.text.isViolation()) {
                    emit(
                        identifier.startOffset,
                        Messages.get(MSG_OTHERS, "Property", identifier.text.transform()),
                        false
                    )
                }
            }
            FUN, CLASS, OBJECT_DECLARATION -> {
                // may be property getter/setter, fun, class, interface, object or annotation
                val typeName = node.children().first {
                    it.elementType != KDOC &&
                        it.elementType != WHITE_SPACE &&
                        it.elementType != MODIFIER_LIST // may be function's annotations
                }.text

                // skip companion object
                val identifier = node.findChildByType(IDENTIFIER) ?: return

                // check for violation
                if (identifier.text.isViolation()) {
                    emit(
                        identifier.startOffset,
                        Messages.get(
                            MSG_OTHERS,
                            typeName.replaceFirstChar { it.uppercase() },
                            identifier.text.transform()
                        ),
                        false
                    )
                }
            }
            FILE -> {
                // get filename, obtained from `com.pinterest.ktlint.ruleset.standard.FilenameRule`
                node as FileASTNode?
                    ?: error("node is not ${FileASTNode::class} but ${node::class}")
                val filePath = (node.psi as? KtFile)?.virtualFilePath
                if (filePath?.endsWith(".kt") != true) {
                    stopTraversalOfAST() // ignore all non ".kt" files (including ".kts")
                    return
                }
                val fileName = Paths.get(filePath).fileName.toString().substringBefore(".")
                if (fileName == "package") {
                    stopTraversalOfAST() // ignore package.kt filename
                    return
                }

                // check for violation
                if (fileName.isViolation()) {
                    emit(node.startOffset, Messages[MSG_FILE], false)
                }
            }
        }
    }

    private fun String.isViolation(): Boolean {
        // find 3 connecting uppercase letters
        for (i in 0 until length - 2) {
            if (get(i).isUpperCase() && get(i + 1).isUpperCase() && get(i + 2).isUpperCase()) {
                return true
            }
        }
        return false
    }

    private fun String.transform(): String {
        val reversed = reversed()
        val sb = StringBuilder()
        reversed.forEachIndexed { index, c ->
            sb.append(
                when {
                    c.isUpperCase() && reversed.getOrNull(index + 1)?.isUpperCase() == true ->
                        c.lowercase()
                    else -> c
                }
            )
        }
        return sb.reverse().toString()
    }
}
