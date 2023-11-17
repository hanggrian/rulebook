package com.example.groovy

import groovy.transform.CompileStatic

@CompileStatic
class UseCommonGenerics {
  class Point<A> {
    A x
  }

  <A> void sum(List<A> list) {
  }
}
