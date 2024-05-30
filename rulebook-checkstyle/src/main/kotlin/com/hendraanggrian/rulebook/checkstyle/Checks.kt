package com.hendraanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.api.AbstractCheck
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck

/** A Checkstyle rule with single configuration of tokens. */
public abstract class Check : AbstractCheck() {
    override fun getDefaultTokens(): IntArray = requiredTokens

    override fun getAcceptableTokens(): IntArray = requiredTokens
}

public typealias JavadocCheck = AbstractJavadocCheck
