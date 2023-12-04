public class StaticInitializerPosition3 {
  private int num;

  public static class InnerClass {
    public static int DEFAULT_VALUE = 0;
  }

  public StaticInitializerPosition3() {
    num = DEFAULT_VALUE;
  }
}
