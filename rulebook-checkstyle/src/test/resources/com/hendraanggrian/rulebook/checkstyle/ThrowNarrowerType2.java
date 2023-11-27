public class ThrowNarrowerType2 {
  public void throwable() throws Throwable {
    throw new Throwable();
  }

  public void exception() throws Exception {
    throw new Exception();
  }

  public void error() {
    throw new Error();
  }
}
