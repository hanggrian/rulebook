public class ThrowSubclass1 {
  public void exception() {
    throw new IllegalStateException();
  }

  public void error() {
    throw new StackOverflowError();
  }
}
