package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class UseAbstractCollectionRuleTest {
    private val assertThatCode = assertThatRule { UseAbstractCollectionRule() }

    @Test
    fun `Abstract collections`() = assertThatCode(
        """
        fun list(collection: List<String>) {
        }
        fun mutableList(collection: MutableList<String>) {
        }
        fun set(collection: Set<String>) {
        }
        fun mutableSet(collection: MutableSet<String>) {
        }
        fun map(collection: Map<Int, String>) {
        }
        fun mutableMap(collection: MutableMap<Int, String>) {
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Explicit collections`() = assertThatCode(
        """
        fun arrayList(collection: ArrayList<String>) {
        }
        fun hashSet(collection: HashSet<String>) {
        }
        fun treeSet(collection: TreeSet<String>) {
        }
        fun hashMap(collection: HashMap<Int, String>) {
        }
        fun treeMap(collection: TreeMap<Int, String>) {
        }
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(1, 27, Messages.get(UseAbstractCollectionRule.MSG_LIST, "ArrayList")),
        LintViolation(3, 25, Messages.get(UseAbstractCollectionRule.MSG_SET, "HashSet")),
        LintViolation(5, 25, Messages.get(UseAbstractCollectionRule.MSG_SET, "TreeSet")),
        LintViolation(7, 25, Messages.get(UseAbstractCollectionRule.MSG_MAP, "HashMap")),
        LintViolation(9, 25, Messages.get(UseAbstractCollectionRule.MSG_MAP, "TreeMap"))
    )

    @Test
    fun `Primary and secondary constructor`() = assertThatCode(
        """
        class Primary(collection: HashSet<String>)
        class Secondary(num: Int) {
            constructor(num: Int, collection: TreeMap<Int, String>) : this(num)
        }
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(1, 27, Messages.get(UseAbstractCollectionRule.MSG_SET, "HashSet")),
        LintViolation(3, 39, Messages.get(UseAbstractCollectionRule.MSG_MAP, "TreeMap"))
    )

    @Test
    fun `Destructuring are skipped`() = assertThatCode(
        """
        fun destructuring(map: Map<Int, ArrayList<String>>) {
            map.forEach { (key: Int, value: ArrayList<String>) ->
            }
        }
        """.trimIndent()
    ).hasNoLintViolations()
}
