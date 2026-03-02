#include <stdio.h>

int main() {
    constexpr int expression = 0;
    if (expression == 12) {
        constexpr int foo = 0;
        printf("%d", foo);
    }
    return 0;
}
