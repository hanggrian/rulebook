package com.example.java;

public class ExceptionExtending {
    public class MyException extends Exception {
        public MyException(final String message) {
            super(message);
        }
    }
}
