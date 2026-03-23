package com.hanggrian.rulebook.internal

import com.hanggrian.rulebook.ktlint.rules.Emit
import com.hanggrian.rulebook.ktlint.rules.RulebookRule
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import java.io.File

class RuleHasSampleRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(FILE)

    override fun visit(node: ASTNode, emit: Emit) {
        var file = node.psi.containingFile.name
        when {
            file.endsWith("RulebookRules.kt") || file.endsWith("RulebookChecks.kt") -> return

            CHECKSTYLE_PATH in file -> {
                File(
                    file
                        .replace(CHECKSTYLE_PATH, "/sample/java/com/example/java/")
                        .replace("Check.kt", ".java"),
                ).takeUnless(File::exists)
                    ?: return
                File(
                    file
                        .replace(CHECKSTYLE_PATH, "/sample/tests/java/com/example/java/")
                        .replace("Check.kt", "Test.java"),
                ).takeUnless(File::exists)
                    ?: return
                emit(node.startOffset, "Missing sample '$file'.", false)
            }

            CODENARC_PATH in file -> {
                File(
                    file
                        .replace(CODENARC_PATH, "/sample/groovy/com/example/groovy/")
                        .replace("Rule.kt", ".groovy"),
                ).takeUnless(File::exists)
                    ?: return
                file =
                    file
                        .replace(CODENARC_PATH, "/sample/gradle/")
                        .replace("Rule.kt", ".gradle")
                val path = file.substringBeforeLast('/')
                var name = file.substringAfterLast('/')
                name =
                    if (name == "RootProjectName.gradle") {
                        "settings.gradle"
                    } else {
                        name.kebabCase
                    }
                File("$path/$name")
                    .takeUnless(File::exists)
                    ?: return
                emit(node.startOffset, "Missing sample '$name'.", false)
            }

            KTLINT_PATH in file -> {
                File(
                    file
                        .replace(KTLINT_PATH, "/sample/kotlin/com/example/kotlin/")
                        .replace("Rule.kt", ".kt"),
                ).takeUnless(File::exists)
                    ?: return
                File(
                    file
                        .replace(KTLINT_PATH, "/sample/tests/kotlin/com/example/kotlin/")
                        .replace("Rule.kt", "Test.kt"),
                ).takeUnless(File::exists)
                    ?: return
                file =
                    file
                        .replace(KTLINT_PATH, "/sample/kts/")
                        .replace("Rule.kt", ".kts")
                val path = file.substringBeforeLast('/')
                var name = file.substringAfterLast('/')
                name =
                    if (name == "RootProjectName.kts") {
                        "settings.gradle.kts"
                    } else {
                        name.kebabCase
                    }
                File("$path/$name")
                    .takeUnless(File::exists)
                    ?: return
                emit(node.startOffset, "Missing sample '$name'.", false)
            }
        }
    }

    companion object {
        val ID: RuleId = RuleId("${CodecheckRuleSet.ID.value}:rule-has-sample")

        const val CHECKSTYLE_PATH =
            "/rulebook-checkstyle/src/main/kotlin/com/hanggrian/rulebook/checkstyle/checks/"
        const val CODENARC_PATH =
            "/rulebook-codenarc/src/main/kotlin/com/hanggrian/rulebook/codenarc/rules/"
        const val KTLINT_PATH =
            "/rulebook-ktlint/src/main/kotlin/com/hanggrian/rulebook/ktlint/rules/"

        val String.kebabCase: String
            get() =
                split(Regex("(?=[A-Z])"))
                    .filterNot { it.isEmpty() }
                    .joinToString("-") { it.lowercase() }
    }
}
