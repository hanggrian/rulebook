package com.example

import groovy.transform.CompileStatic

@CompileStatic
class UseCommonGenerics {
  class Point<N> {
    N x
  }

  <E> void sum(List<E> list) {
  }
}
