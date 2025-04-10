package com.hanggrian.rulebook.codenarc.internals

/** Returns the code without EOL comment. */
internal fun String.trimComment() = substringBefore("//").trimEnd()
