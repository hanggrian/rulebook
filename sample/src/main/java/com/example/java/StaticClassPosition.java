package com.example.java;

public class StaticClassPosition {
  public static class Foo {
    public static class Bar {
      public static final int VALUE = 0;
    }

    public Foo() {
      this(Bar.VALUE);
    }

    public Foo(int a) {}
  }
}
