class ConstructorPosition {
  public class Foo {
    public void baz() {
    }

    public Foo() {
      this(0);
    }

    public Foo(int a) {
    }
  }
}
