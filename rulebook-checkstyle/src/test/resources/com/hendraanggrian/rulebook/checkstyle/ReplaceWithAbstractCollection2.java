import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class ReplaceWithAbstractCollection2 {
  public void arrayList(ArrayList<Integer> collection) { // VIOLATION
  }

  public void hashSet(HashSet<Integer> collection) { // VIOLATION
  }

  public void treeSet(TreeSet<Integer> collection) { // VIOLATION
  }

  public void hashMap(HashMap<Integer, Integer> collection) { // VIOLATION
  }

  public void map(TreeMap<Integer, Integer> collection) { // VIOLATION
  }
}
