package com.hendraanggrian.codestyle.checkstyle;

class MyClass {
    /**
     * @param input [Int] // violation
     * @param input a [Int] // violation
     * @param input [Int] a // violation
     * @param input [Int]. // fine because suffix is period
     */
    public void add(int input) {
    }
}
