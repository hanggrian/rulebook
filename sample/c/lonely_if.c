#include <stdio.h>

int lonely_if() {
    constexpr int expression = 0;
    if (expression == 1) {
        return 0;
    }
    if (expression == 2) {
        return 1;
    }
    return 2;
}
