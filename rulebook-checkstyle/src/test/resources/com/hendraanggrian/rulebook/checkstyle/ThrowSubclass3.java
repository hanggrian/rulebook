public class ThrowSubclass3 {
  public void throwable() throws Throwable {
    Throwable throwable = new Throwable();
    throw throwable;
  }

  public void exception() throws Exception {
    Exception exception = new Exception();
    throw exception;
  }

  public void error() {
    Error error = new Error();
    throw error;
  }
}
