import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReplaceWithAbstractType3 {
  class MyList {
    public MyList(List<Integer> collection) {
    }
  }

  class MySet {
    public MySet(Set<Integer> collection) {
    }
  }

  class MyMap {
    public MyMap(Map<Integer, Integer> collection) {
    }
  }

  class MyInputStream {
    public MyInputStream(InputStream io) {
    }
  }

  class MyOutputStream {
    public MyOutputStream(OutputStream io) {
    }
  }
}
