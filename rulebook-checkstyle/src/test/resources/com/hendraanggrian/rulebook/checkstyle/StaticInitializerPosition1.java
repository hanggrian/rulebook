public class StaticInitializerPosition1 {
  private int num;

  public StaticInitializerPosition1() {
    num = DEFAULT_VALUE;
  }

  public static class InnerClass {
    public static int DEFAULT_VALUE = 0;
  }
}
