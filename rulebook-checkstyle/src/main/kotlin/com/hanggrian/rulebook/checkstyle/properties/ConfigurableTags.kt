package com.hanggrian.rulebook.checkstyle.properties

public interface ConfigurableTags {
    public val tagSet: HashSet<String>

    public fun setTags(vararg tags: String) {
        tagSet.clear()
        tagSet += tags
    }
}
