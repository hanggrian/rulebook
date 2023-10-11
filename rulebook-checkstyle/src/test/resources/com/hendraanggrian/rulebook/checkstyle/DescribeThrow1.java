public class DescribeThrow1 {
  public void throwable() throws Throwable {
    throw new Throwable("Hello World");
  }

  public void exception() throws Exception {
    throw new Exception("Hello World");
  }

  public void error() {
    throw new Error("Hello World");
  }
}
