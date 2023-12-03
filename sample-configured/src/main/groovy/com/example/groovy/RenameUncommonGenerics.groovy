package com.example.groovy

class RenameUncommonGenerics {
  class Point<A> {
    A x
  }

  <A> void sum(List<A> list) {
  }
}
