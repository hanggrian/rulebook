package com.hanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.api.AbstractCheck
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck

/** A Checkstyle rule with single configuration of tokens. */
public abstract class Check : AbstractCheck() {
    final override fun getDefaultTokens(): IntArray = requiredTokens

    final override fun getAcceptableTokens(): IntArray = requiredTokens
}

public typealias JavadocCheck = AbstractJavadocCheck
