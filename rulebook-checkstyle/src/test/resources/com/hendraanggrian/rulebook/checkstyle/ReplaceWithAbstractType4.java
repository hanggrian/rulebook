import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class ReplaceWithAbstractType4 {
  class MyArrayList {
    public MyArrayList(ArrayList<Integer> collection) { // VIOLATION
    }
  }

  class MyHashSet {
    public MyHashSet(HashSet<Integer> collection) { // VIOLATION
    }
  }

  class MyTreeSet {
    public MyTreeSet(TreeSet<Integer> collection) { // VIOLATION
    }
  }

  class MyHashMap {
    public MyHashMap(HashMap<Integer, Integer> collection) { // VIOLATION
    }
  }

  class MyTreeMap {
    public MyTreeMap(TreeMap<Integer, Integer> collection) { // VIOLATION
    }
  }

  class MyInputStream {
    public MyInputStream(FileInputStream io) { // VIOLATION
    }
  }

  class MyOutputStream {
    public MyOutputStream(FileOutputStream io) { // VIOLATION
    }
  }
}
