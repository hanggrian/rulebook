public class DescribeThrow2 {
  public void throwable() throws Throwable {
    throw new Throwable(); // VIOLATION
  }

  public void exception() throws Exception {
    throw new Exception(); // VIOLATION
  }

  public void error() {
    throw new Error(); // VIOLATION
  }
}
