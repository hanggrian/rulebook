public class StaticInitializerPosition2 {
  public StaticInitializerPosition2() {
    num = DEFAULT_VALUE;
  }

  public static class InnerClass {
    public static int DEFAULT_VALUE = 0;
  }

  private int num;
}
