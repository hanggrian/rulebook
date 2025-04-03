package com.hanggrian.rulebook.checkstyle

import com.google.common.truth.Truth.assertThat
import com.puppycrawl.tools.checkstyle.Checker
import com.puppycrawl.tools.checkstyle.DefaultConfiguration
import com.puppycrawl.tools.checkstyle.api.CheckstyleException
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck
import java.io.File

@Throws(CheckstyleException::class)
inline fun <reified T> checkerOf(): Checker {
    val checker = Checker()
    checker.setModuleClassLoader(Thread.currentThread().contextClassLoader)
    checker.configure(
        DefaultConfiguration("Checks").apply {
            addChild(DefaultConfiguration(T::class.java.canonicalName))
        },
    )
    return checker
}

@Throws(CheckstyleException::class)
inline fun <reified T> treeWalkerCheckerOf(): Checker {
    val checker = Checker()
    checker.setModuleClassLoader(Thread.currentThread().contextClassLoader)
    checker.configure(
        DefaultConfiguration("Checks").apply {
            addChild(
                DefaultConfiguration("TreeWalker").apply {
                    addChild(DefaultConfiguration(T::class.java.canonicalName))
                },
            )
        },
    )
    return checker
}

fun Checker.read(resource: String): Int {
    val testFileUrl = object {}.javaClass.getResource("$resource.java")!!
    val testFile = File(testFileUrl.file)
    return process(listOf(testFile))
}

fun RulebookAstCheck.assertProperties() {
    val requiredTokens = requiredTokens.asList()
    assertThat(defaultTokens).asList().containsExactlyElementsIn(requiredTokens)
    assertThat(acceptableTokens).asList().containsExactlyElementsIn(requiredTokens)
}

fun RulebookFileCheck.assertProperties() = assertThat(fileExtensions).asList().contains(".java")

fun AbstractJavadocCheck.assertProperties() = assertThat(defaultJavadocTokens).isNotEmpty()
