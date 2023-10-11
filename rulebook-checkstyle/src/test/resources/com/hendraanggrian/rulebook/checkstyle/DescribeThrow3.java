public class DescribeThrow2 {
  public void exception() {
    throw new IllegalStateException("Hello World");
  }

  public void error() {
    throw new StackOverflowError("Hello World");
  }
}
