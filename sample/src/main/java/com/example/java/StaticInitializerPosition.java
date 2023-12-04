package com.example.java;

public class StaticInitializerPosition {
  public static class InnerClass {
    public static final int DEFAULT_VALUE = 0;
  }

  int num;

  public StaticInitializerPosition(String name) {
    num = InnerClass.DEFAULT_VALUE;
  }
}
