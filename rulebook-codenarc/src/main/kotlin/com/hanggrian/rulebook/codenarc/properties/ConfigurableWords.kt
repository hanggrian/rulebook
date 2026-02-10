package com.hanggrian.rulebook.codenarc.properties

import com.hanggrian.rulebook.codenarc.splitToList

public interface ConfigurableWords {
    public val wordSet: HashSet<String>

    public fun setWords(words: String) {
        wordSet.clear()
        wordSet += words.splitToList()
    }
}
