package com.hendraanggrian.rulebook.checkstyle;

public class ExceptionAmbiguity {
  public void throwCallException() throws Exception {
    throw new Exception();
  }

  public void throwReferenceException() throws Exception {
    Exception error = new Exception();
    throw error;
  }
}
