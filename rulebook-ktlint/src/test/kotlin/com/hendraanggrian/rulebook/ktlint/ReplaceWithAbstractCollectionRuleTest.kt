package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ReplaceWithAbstractCollectionRuleTest {
    private val assertThatCode = assertThatRule { ReplaceWithAbstractCollectionRule() }

    @Test
    fun `Use collections as function parameter`() = assertThatCode(
        """
        fun list(collection: List<Int>) {}
        fun mutableList(collection: MutableList<Int>) {}
        fun set(collection: Set<Int>) {}
        fun mutableSet(collection: MutableSet<Int>) {}
        fun map(collection: Map<Int, Int>) {}
        fun mutableMap(collection: MutableMap<Int, Int>) {}
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Use explicit collections as function parameter`() = assertThatCode(
        """
        fun arrayList(collection: ArrayList<Int>) {}
        fun hashSet(collection: HashSet<Int>) {}
        fun treeSet(collection: TreeSet<Int>) {}
        fun hashMap(collection: HashMap<Int, Int>) {}
        fun treeMap(collection: TreeMap<Int, Int>) {}
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(1, 27, Messages[ReplaceWithAbstractCollectionRule.MSG_LIST]),
        LintViolation(2, 25, Messages[ReplaceWithAbstractCollectionRule.MSG_SET]),
        LintViolation(3, 25, Messages[ReplaceWithAbstractCollectionRule.MSG_SET]),
        LintViolation(4, 25, Messages[ReplaceWithAbstractCollectionRule.MSG_MAP]),
        LintViolation(5, 25, Messages[ReplaceWithAbstractCollectionRule.MSG_MAP])
    )

    @Test
    fun `Use collections as constructor parameter`() = assertThatCode(
        """
        class MyList(collection: List<Int>)
        class MyMutableList(collection: MutableList<Int>)
        class MySet(collection: Set<Int>)
        class MyMutableSet(collection: MutableSet<Int>)
        class MyMap(collection: Map<Int, Int>)
        class MyMutableMap(collection: MutableMap<Int, Int>)
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Use explicit collections as constructor parameter`() = assertThatCode(
        """
        class MyArrayList(collection: ArrayList<Int>)
        class MyHashSet(collection: HashSet<Int>)
        class MyTreeSet(collection: TreeSet<Int>)
        class MyHashMap(collection: HashMap<Int, Int>)
        class MyTreeMap(collection: TreeMap<Int, Int>)
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(1, 31, Messages[ReplaceWithAbstractCollectionRule.MSG_LIST]),
        LintViolation(2, 29, Messages[ReplaceWithAbstractCollectionRule.MSG_SET]),
        LintViolation(3, 29, Messages[ReplaceWithAbstractCollectionRule.MSG_SET]),
        LintViolation(4, 29, Messages[ReplaceWithAbstractCollectionRule.MSG_MAP]),
        LintViolation(5, 29, Messages[ReplaceWithAbstractCollectionRule.MSG_MAP])
    )

    @Test
    fun `Using explicit collection as secondary constructor parameter`() = assertThatCode(
        """
        class MyClass(num: Int) {
            constructor(num: Int, collection: TreeMap<Int, Int>) : this(num)
        }
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(2, 39, Messages[ReplaceWithAbstractCollectionRule.MSG_MAP])
    )

    @Test
    fun `Using explicit collection as destructuring parameter`() = assertThatCode(
        """
        fun destructuring(collection: Map<Int, ArrayList<Int>>) {
            map.forEach { (key: Int, value: ArrayList<Int>) ->
            }
        }
        """.trimIndent()
    ).hasNoLintViolations()
}
