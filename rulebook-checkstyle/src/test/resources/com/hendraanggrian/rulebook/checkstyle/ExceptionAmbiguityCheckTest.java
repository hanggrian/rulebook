package com.hendraanggrian.rulebook.checkstyle;

public class ExceptionAmbiguity {
  public void throwException() throws Exception {
    throw new Exception();
  }

  public void throwExceptionWithMessage() throws Exception {
    throw new Exception("Some message");
  }

  public void throwIllegalStateException() throws Exception {
    throw new IllegalStateException();
  }
}
