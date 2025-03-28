package com.hanggrian.rulebook.codenarc.internals

internal fun String.trimComment() = substringBefore("//").trimEnd()
