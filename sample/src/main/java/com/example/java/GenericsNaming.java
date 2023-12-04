package com.example.java;

import java.util.List;

public class GenericsNaming {
  public class Point<G> {
    G x;
    G y;
  }

  public <X> void sum(List<X> list) {
  }
}
