package com.example.groovy

class InvertIfCondition {
    void main(int i, int j) {
        if (!(i < j)) {
            return
        }
        int k = i
        k += j
    }
}
