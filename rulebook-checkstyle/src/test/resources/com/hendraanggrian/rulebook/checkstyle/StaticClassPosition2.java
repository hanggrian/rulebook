public class StaticClassPosition {
  public class Foo {
    public static class Bar {
      public static int VALUE = 0;
    }

    public int bar = VALUE;
  }
}
