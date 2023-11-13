package com.hendraanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.api.AbstractCheck

/**
 * A Checkstyle rule with single configuration of tokens.
 */
public sealed class RulebookCheck : AbstractCheck() {
    public override fun getDefaultTokens(): IntArray = requiredTokens

    public override fun getAcceptableTokens(): IntArray = requiredTokens
}
