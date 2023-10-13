package com.example

import groovy.transform.CompileStatic

@CompileStatic
class RenameGenerics {
  class Point<N> {
    N x
  }

  <E> void sum(List<E> list) {
    list.size()
  }
}
