package com.example;

public class ExceptionAmbiguity {
  public void throwCallExpression() throws Exception {
    throw new Exception();
  }

  public void throwReferenceExpression() throws Exception {
    Exception error = new Exception();
    throw error;
  }
}
