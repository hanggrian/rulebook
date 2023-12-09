public class ConstructorPosition {
  public class Foo {
    public Foo() {
      this(0);
    }

    public Foo(int a) {
    }

    public int bar = 0;
  }
}
