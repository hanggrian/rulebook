package com.hendraanggrian.rulebook

import com.hendraanggrian.rulebook.codenarc.Messages
import com.hendraanggrian.rulebook.codenarc.UseAbstractCollectionNarc
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertEquals

class UseAbstractCollectionNarcTest : AbstractRuleTestCase<UseAbstractCollectionNarc>() {
    override fun createRule(): UseAbstractCollectionNarc = UseAbstractCollectionNarc()

    @Test
    fun `Rule properties`() {
        assertEquals(3, rule.priority)
        assertEquals("UseAbstractCollection", rule.name)
    }

    @Test
    fun `Use collections as function parameter`() = assertNoViolations(
        """
        void list(List<Integer> collection) {}
        void set(Set<Integer> collection) {}
        void map(Map<Integer, Integer> collection) {}
        """.trimIndent()
    )

    @Test
    fun `Use explicit collections as function parameter`() = assertViolations(
        """
        void arrayList(ArrayList<Int> collection) {}
        void hashSet(HashSet<Int> collection) {}
        void treeSet(TreeSet<Int> collection) {}
        void hashMap(HashMap<Int, Int> collection) {}
        void treeMap(TreeMap<Int, Int> collection) {}
        """.trimIndent(),
        mutableMapOf(
            "line" to 1,
            "source" to "void arrayList(ArrayList<Int> collection) {}",
            "message" to Messages[UseAbstractCollectionNarc.MSG_LIST]
        ),
        mutableMapOf(
            "line" to 2,
            "source" to "void hashSet(HashSet<Int> collection) {}",
            "message" to Messages[UseAbstractCollectionNarc.MSG_SET]
        ),
        mutableMapOf(
            "line" to 3,
            "source" to "treeSet(TreeSet<Int> collection) {}",
            "message" to Messages[UseAbstractCollectionNarc.MSG_SET]
        ),
        mutableMapOf(
            "line" to 4,
            "source" to "hashMap(HashMap<Int, Int> collection) {}",
            "message" to Messages[UseAbstractCollectionNarc.MSG_MAP]
        ),
        mutableMapOf(
            "line" to 5,
            "source" to "treeMap(TreeMap<Int, Int> collection) {}",
            "message" to Messages[UseAbstractCollectionNarc.MSG_MAP]
        )
    )

    @Test
    fun `Use collections as constructor parameter`() = assertNoViolations(
        """
        class MyList {
          public MyList(List<Integer> collection) {}
        }
        class MySet {
          public MySet(Set<Integer> collection) {}
        }
        class MyMap {
          public MyMap(Map<Integer, Integer> collection) {}
        }
        """.trimIndent()
    )

    @Test
    fun `Use explicit collections as constructor parameter`() = assertViolations(
        """
        class MyArrayList {
          public MyArrayList(ArrayList<Integer> collection) {}
        }
        class MyHashSet {
          public MyHashSet(HashSet<Integer> collection) {}
        }
        class MyTreeSet {
          public MyTreeSet(TreeSet<Integer> collection) {}
        }
        class MyHashMap {
          public MyHashMap(HashMap<Integer, Integer> collection) {}
        }
        class MyTreeMap {
          public MyTreeMap(TreeMap<Integer, Integer> collection) {}
        }
        """.trimIndent(),
        mutableMapOf(
            "line" to 2,
            "source" to "public MyArrayList(ArrayList<Integer> collection) {}",
            "message" to Messages[UseAbstractCollectionNarc.MSG_LIST]
        ),
        mutableMapOf(
            "line" to 5,
            "source" to "public MyHashSet(HashSet<Integer> collection) {}",
            "message" to Messages[UseAbstractCollectionNarc.MSG_SET]
        ),
        mutableMapOf(
            "line" to 8,
            "source" to "public MyTreeSet(TreeSet<Integer> collection) {}",
            "message" to Messages[UseAbstractCollectionNarc.MSG_SET]
        ),
        mutableMapOf(
            "line" to 11,
            "source" to "public MyHashMap(HashMap<Integer, Integer> collection) {}",
            "message" to Messages[UseAbstractCollectionNarc.MSG_MAP]
        ),
        mutableMapOf(
            "line" to 14,
            "source" to "public MyTreeMap(TreeMap<Integer, Integer> collection) {}",
            "message" to Messages[UseAbstractCollectionNarc.MSG_MAP]
        )
    )
}
