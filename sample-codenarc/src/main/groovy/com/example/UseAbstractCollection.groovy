package com.example

import groovy.transform.CompileStatic

@CompileStatic
class UseAbstractCollection {
  void listFunction(List<String> collection) {
      collection.hashCode()
  }

  class Constructors {
    Constructors(Set<String> collection) {
        collection.hashCode()
    }

    Constructors(Map<Integer, String> collection) {
        collection.hashCode()
    }
  }
}
