package com.hendraanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.Checker
import com.puppycrawl.tools.checkstyle.DefaultConfiguration
import com.puppycrawl.tools.checkstyle.api.CheckstyleException
import java.io.File

@Throws(CheckstyleException::class)
inline fun <reified T> prepareChecker(): Checker {
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

fun prepareFiles(fileName: String): List<File> {
    val testFileUrl = object {}.javaClass.getResource("$fileName.java")!!
    val testFile = File(testFileUrl.file)
    return listOf(testFile)
}
