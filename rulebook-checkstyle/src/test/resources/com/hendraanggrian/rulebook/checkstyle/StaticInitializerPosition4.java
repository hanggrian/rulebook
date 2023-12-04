public class StaticInitializerPosition4 {
  private int num;

  public StaticInitializerPosition4() {
    num = DEFAULT_VALUE;
  }

  public static class InnerClass {
    public static int DEFAULT_VALUE = 0;
  }

  public void increment() {
    num++;
  }
}
