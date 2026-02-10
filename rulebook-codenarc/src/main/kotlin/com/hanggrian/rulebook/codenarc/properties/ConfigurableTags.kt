package com.hanggrian.rulebook.codenarc.properties

import com.hanggrian.rulebook.codenarc.splitToList

public interface ConfigurableTags {
    public val tagSet: HashSet<String>

    public fun setTags(tags: String) {
        tagSet.clear()
        tagSet += tags.splitToList()
    }
}
