package com.hanggrian.rulebook.checkstyle.checks

import com.puppycrawl.tools.checkstyle.api.AbstractCheck
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck
import com.puppycrawl.tools.checkstyle.api.FileText
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck
import java.io.File

/** Rule that uses Java AST tree to validate a node. */
public abstract class RulebookAstCheck : AbstractCheck() {
    final override fun getDefaultTokens(): IntArray = requiredTokens

    final override fun getAcceptableTokens(): IntArray = requiredTokens
}

/** Rule that checks source code by the raw file. */
public abstract class RulebookFileCheck : AbstractFileSetCheck() {
    protected lateinit var lines: Array<String>

    init {
        setFileExtensions("java")
        tabWidth = 4
    }

    override fun processFiltered(file: File, fileText: FileText) {
        lines = fileText.toLinesArray()
    }
}

/** An alias of base javadoc check. */
public typealias RulebookJavadocCheck = AbstractJavadocCheck
