package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.ReplaceWithAbstractTypeRule.Companion.MSG_COLLECTION
import com.hendraanggrian.rulebook.ktlint.ReplaceWithAbstractTypeRule.Companion.MSG_IO
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ReplaceWithAbstractTypeRuleTest {
    private val assertThatCode = assertThatRule { ReplaceWithAbstractTypeRule() }

    @Test
    fun `Use abstract types as function parameter`() = assertThatCode(
        """
        fun list(collection: List<Int>) {}
        fun mutableList(collection: MutableList<Int>) {}
        fun set(collection: Set<Int>) {}
        fun mutableSet(collection: MutableSet<Int>) {}
        fun map(collection: Map<Int, Int>) {}
        fun mutableMap(collection: MutableMap<Int, Int>) {}

        fun read(io: InputStream) {}
        fun write(io: OutputStream) {}
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Use explicit types as function parameter`() = assertThatCode(
        """
        fun arrayList(collection: ArrayList<Int>) {}
        fun hashSet(collection: HashSet<Int>) {}
        fun treeSet(collection: TreeSet<Int>) {}
        fun hashMap(collection: HashMap<Int, Int>) {}
        fun treeMap(collection: TreeMap<Int, Int>) {}

        fun read(io: FileInputStream) {}
        fun write(io: FileOutputStream) {}
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(1, 27, Messages.get(MSG_COLLECTION, "List", "MutableList")),
        LintViolation(2, 25, Messages.get(MSG_COLLECTION, "Set", "MutableSet")),
        LintViolation(3, 25, Messages.get(MSG_COLLECTION, "Set", "MutableSet")),
        LintViolation(4, 25, Messages.get(MSG_COLLECTION, "Map", "MutableMap")),
        LintViolation(5, 25, Messages.get(MSG_COLLECTION, "Map", "MutableMap")),
        LintViolation(7, 14, Messages.get(MSG_IO, "InputStream")),
        LintViolation(8, 15, Messages.get(MSG_IO, "OutputStream"))
    )

    @Test
    fun `Use abstract types as constructor parameter`() = assertThatCode(
        """
        class MyList(collection: List<Int>)
        class MyMutableList(collection: MutableList<Int>)
        class MySet(collection: Set<Int>)
        class MyMutableSet(collection: MutableSet<Int>)
        class MyMap(collection: Map<Int, Int>)
        class MyMutableMap(collection: MutableMap<Int, Int>)

        class MyReader(io: InputStream)
        class MyWriter(io: OutputStream)
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Use explicit types as constructor parameter`() = assertThatCode(
        """
        class MyArrayList(collection: ArrayList<Int>)
        class MyHashSet(collection: HashSet<Int>)
        class MyTreeSet(collection: TreeSet<Int>)
        class MyHashMap(collection: HashMap<Int, Int>)
        class MyTreeMap(collection: TreeMap<Int, Int>)

        class MyReader(io: FileInputStream)
        class MyWriter(io: FileOutputStream)
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(1, 31, Messages.get(MSG_COLLECTION, "List", "MutableList")),
        LintViolation(2, 29, Messages.get(MSG_COLLECTION, "Set", "MutableSet")),
        LintViolation(3, 29, Messages.get(MSG_COLLECTION, "Set", "MutableSet")),
        LintViolation(4, 29, Messages.get(MSG_COLLECTION, "Map", "MutableMap")),
        LintViolation(5, 29, Messages.get(MSG_COLLECTION, "Map", "MutableMap")),
        LintViolation(7, 20, Messages.get(MSG_IO, "InputStream")),
        LintViolation(8, 20, Messages.get(MSG_IO, "OutputStream"))
    )

    @Test
    fun `Secondary constructor parameter`() = assertThatCode(
        """
        class MyClass(num: Int) {
            constructor(num: Int, collection: TreeMap<Int, Int>) : this(num)
            constructor(num: Int, io: FileInputStream) : this(num)
        }
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(2, 39, Messages.get(MSG_COLLECTION, "Map", "MutableMap")),
        LintViolation(3, 31, Messages.get(MSG_IO, "InputStream"))
    )

    @Test
    fun `Destructuring parameter`() = assertThatCode(
        """
        fun destructuring(collection: Map<Int, ArrayList<Int>>) {
            map.forEach { (key: Int, value: ArrayList<Int>) ->
            }
        }
        """.trimIndent()
    ).hasNoLintViolations()
}
