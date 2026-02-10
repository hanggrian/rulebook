package com.hanggrian.rulebook.checkstyle.properties

public interface ConfigurableWords {
    public val wordSet: HashSet<String>

    public fun setWords(vararg word: String) {
        wordSet.clear()
        wordSet += word
    }
}
