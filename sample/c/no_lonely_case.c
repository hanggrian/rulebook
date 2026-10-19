#include <stdio.h>

static int lonely_case() {
    constexpr int expression = 0;
    if (expression == 12) {
        constexpr int foo = 0;
        printf("%d", foo);
    }
    return 0;
}

int main() {
    lonely_case();
    return 0;
}
