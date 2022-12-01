package com.hendraanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.Checker
import com.puppycrawl.tools.checkstyle.DefaultConfiguration
import com.puppycrawl.tools.checkstyle.api.CheckstyleException
import java.io.File

@Throws(CheckstyleException::class)
inline fun <reified T> prepareChecker(): Checker {
    val checker = Checker()
    checker.setModuleClassLoader(Thread.currentThread().contextClassLoader)
    checker.configure(prepareConfiguration<T>())
    return checker
}

inline fun <reified T> prepareConfiguration(): DefaultConfiguration {
    val checks = DefaultConfiguration("Checks")
    val treeWalker = DefaultConfiguration("TreeWalker")
    val checkToTest = DefaultConfiguration(T::class.java.canonicalName)
    checks.addChild(treeWalker)
    treeWalker.addChild(checkToTest)
    return checks
}

inline fun <reified T> prepareFiles(
    testClassName: String = "${T::class.java.simpleName}.java"
): List<File> {
    val testFileUrl = T::class.java.getResource(testClassName)!!
    val testFile = File(testFileUrl.file)
    return mutableListOf(testFile)
}
