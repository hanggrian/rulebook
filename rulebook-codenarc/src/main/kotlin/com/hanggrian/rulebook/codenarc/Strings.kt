package com.hanggrian.rulebook.codenarc

internal const val JAVADOC_START = """/\*\*\s*\n"""
internal const val JAVADOC_ANY_LINES = """(\s*\*\s*.*\n)*"""
internal const val JAVADOC_END = """\s*\*/"""
internal const val JAVADOC_LINE_PREFIX = """\s*\*\s*"""

internal val JAVADOC_REGEX = Regex("""$JAVADOC_START$JAVADOC_ANY_LINES$JAVADOC_END""")

/** Returns the code without EOL comment. */
internal fun String.trimComment() = substringBefore("//").trimEnd()

/** Interpret XML configuration as a collection. */
internal fun String?.splitToList() = this?.split(',')?.map { it.trim() } ?: emptyList()
