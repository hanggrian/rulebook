package com.example;

import java.util.List;

public class RenameUncommonGenerics {
  public class Point<G> {
    G x;
    G y;
  }

  public <X> void sum(List<X> list) {
  }
}
