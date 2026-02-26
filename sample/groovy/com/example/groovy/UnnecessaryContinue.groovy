package com.example.groovy

def unnecessaryContinue(int... items) {
    for (int item : items) {
        println('foo')
        // continue
    }
}
