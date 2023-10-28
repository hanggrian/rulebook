package com.hendraanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.api.AbstractCheck

/**
 * A Checkstyle rule with single configuration of tokens.
 */
abstract class RulebookCheck : AbstractCheck() {
    override fun getDefaultTokens(): IntArray = requiredTokens

    override fun getAcceptableTokens(): IntArray = requiredTokens
}
