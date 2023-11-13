package com.hendraanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.Checker
import com.puppycrawl.tools.checkstyle.DefaultConfiguration
import com.puppycrawl.tools.checkstyle.api.CheckstyleException
import java.io.File
import kotlin.reflect.KClass

@Throws(CheckstyleException::class)
public fun prepareChecker(vararg types: KClass<*>): Checker {
    val checker = Checker()
    checker.setModuleClassLoader(Thread.currentThread().contextClassLoader)
    checker.configure(
        DefaultConfiguration("Checks").apply {
            addChild(
                DefaultConfiguration("TreeWalker").apply {
                    types.forEach { addChild(DefaultConfiguration(it.java.canonicalName)) }
                },
            )
        },
    )
    return checker
}

public fun prepareFiles(fileName: String): List<File> {
    val testFileUrl = object {}.javaClass.getResource("$fileName.java")!!
    val testFile = File(testFileUrl.file)
    return listOf(testFile)
}
