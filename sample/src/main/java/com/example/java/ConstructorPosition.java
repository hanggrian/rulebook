package com.example.java;

public class ConstructorPosition {
  public static class Foo {
    public void baz() {}

    public Foo() {
      this(0);
    }

    public Foo(int a) {}

    public int bar = 0;
  }
}
