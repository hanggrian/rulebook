package com.hendraanggrian.rulebook.checkstyle;

import java.util.*;

class UseAbstractCollection {
  UseAbstractCollectionRule(List<String> collection) {
  }
  UseAbstractCollectionRule(Set<String> collection) {
  }
  UseAbstractCollectionRule(Map<Int, String> collection) {
  }

  void arrayList(ArrayList<String> collection) {
  }
  void hashSet(HashSet<String> collection) {
  }
  void treeSet(TreeSet<String> collection) {
  }
  void hashMap(HashMap<Int, String> collection) {
  }
  void treeMap(TreeMap<Int, String> collection) {
  }
}
