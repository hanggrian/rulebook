package com.hendraanggrian.rulebook

import com.hendraanggrian.rulebook.codenarc.Messages
import com.hendraanggrian.rulebook.codenarc.ReplaceWithAbstractTypeNarc
import com.hendraanggrian.rulebook.codenarc.ReplaceWithAbstractTypeNarc.Companion.MSG
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertEquals

class ReplaceWithAbstractTypeNarcTest : AbstractRuleTestCase<ReplaceWithAbstractTypeNarc>() {
    override fun createRule(): ReplaceWithAbstractTypeNarc = ReplaceWithAbstractTypeNarc()

    @Test
    fun `Rule properties`() {
        assertEquals(3, rule.priority)
        assertEquals("ReplaceWithAbstractType", rule.name)
    }

    @Test
    fun `Use abstract types as function parameter`() = assertNoViolations(
        """
        void list(List<Integer> collection) {}
        void set(Set<Integer> collection) {}
        void map(Map<Integer, Integer> collection) {}

        void read(InputStream io) {}
        void write(OutputStream io) {}
        """.trimIndent()
    )

    @Test
    fun `Use explicit types as function parameter`() = assertViolations(
        """
        void arrayList(ArrayList<Int> collection) {}
        void hashSet(HashSet<Int> collection) {}
        void treeSet(TreeSet<Int> collection) {}
        void hashMap(HashMap<Int, Int> collection) {}
        void treeMap(TreeMap<Int, Int> collection) {}

        void read(FileInputStream io) {}
        void write(FileOutputStream io) {}
        """.trimIndent(),
        mapOf(
            "line" to 1,
            "source" to "void arrayList(ArrayList<Int> collection) {}",
            "message" to Messages.get(MSG, "List")
        ),
        mapOf(
            "line" to 2,
            "source" to "void hashSet(HashSet<Int> collection) {}",
            "message" to Messages.get(MSG, "Set")
        ),
        mapOf(
            "line" to 3,
            "source" to "treeSet(TreeSet<Int> collection) {}",
            "message" to Messages.get(MSG, "Set")
        ),
        mapOf(
            "line" to 4,
            "source" to "hashMap(HashMap<Int, Int> collection) {}",
            "message" to Messages.get(MSG, "Map")
        ),
        mapOf(
            "line" to 5,
            "source" to "treeMap(TreeMap<Int, Int> collection) {}",
            "message" to Messages.get(MSG, "Map")
        ),
        mapOf(
            "line" to 7,
            "source" to "void read(FileInputStream io) {}",
            "message" to Messages.get(MSG, "InputStream")
        ),
        mapOf(
            "line" to 8,
            "source" to "void write(FileOutputStream io) {}",
            "message" to Messages.get(MSG, "OutputStream")
        )
    )

    @Test
    fun `Use abstract types as constructor parameter`() = assertNoViolations(
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

        class MyReader {
          public MyReader(InputStream io) {}
        }
        class MyWriter {
          public MyWriter(OutputStream io) {}
        }
        """.trimIndent()
    )

    @Test
    fun `Use explicit types as constructor parameter`() = assertViolations(
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

        class MyReader {
          public MyReader(FileInputStream io) {}
        }
        class MyWriter {
          public MyWriter(FileOutputStream io) {}
        }
        """.trimIndent(),
        mapOf(
            "line" to 2,
            "source" to "public MyArrayList(ArrayList<Integer> collection) {}",
            "message" to Messages.get(MSG, "List")
        ),
        mapOf(
            "line" to 5,
            "source" to "public MyHashSet(HashSet<Integer> collection) {}",
            "message" to Messages.get(MSG, "Set")
        ),
        mapOf(
            "line" to 8,
            "source" to "public MyTreeSet(TreeSet<Integer> collection) {}",
            "message" to Messages.get(MSG, "Set")
        ),
        mapOf(
            "line" to 11,
            "source" to "public MyHashMap(HashMap<Integer, Integer> collection) {}",
            "message" to Messages.get(MSG, "Map")
        ),
        mapOf(
            "line" to 14,
            "source" to "public MyTreeMap(TreeMap<Integer, Integer> collection) {}",
            "message" to Messages.get(MSG, "Map")
        ),
        mapOf(
            "line" to 18,
            "source" to "public MyReader(FileInputStream io) {}",
            "message" to Messages.get(MSG, "InputStream")
        ),
        mapOf(
            "line" to 21,
            "source" to "public MyWriter(FileOutputStream io) {}",
            "message" to Messages.get(MSG, "OutputStream")
        )
    )
}
