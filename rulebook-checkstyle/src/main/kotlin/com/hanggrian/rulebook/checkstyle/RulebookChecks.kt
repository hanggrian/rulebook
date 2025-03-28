package com.hanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.api.AbstractCheck
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck

/** Rule that uses Java AST tree to validate a node. */
public abstract class RulebookAstCheck : AbstractCheck() {
    final override fun getDefaultTokens(): IntArray = requiredTokens

    final override fun getAcceptableTokens(): IntArray = requiredTokens
}

/** Rule that checks source code by the raw file. */
public abstract class RulebookFileCheck : AbstractFileSetCheck() {
    init {
        setFileExtensions("java")
        tabWidth = 4
    }
}

/** An alias of base javadoc check. */
public typealias RulebookJavadocCheck = AbstractJavadocCheck
