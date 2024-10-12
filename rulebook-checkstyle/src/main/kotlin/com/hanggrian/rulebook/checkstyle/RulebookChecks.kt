package com.hanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.api.AbstractCheck
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck

/** A Checkstyle rule with single configuration of tokens. */
public abstract class RulebookCheck : AbstractCheck() {
    final override fun getDefaultTokens(): IntArray = requiredTokens

    final override fun getAcceptableTokens(): IntArray = requiredTokens
}

/** An alias of base javadoc check. */
public typealias RulebookJavadocCheck = AbstractJavadocCheck
