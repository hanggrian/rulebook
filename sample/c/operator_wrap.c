#include <stdio.h>

static void operator_wrap() {
    constexpr int foo =
        0 +
        1;
    constexpr bool bar =
        false +
        false +
        true;

    printf("%d %d", foo, bar);
}

int main() {
    operator_wrap();
    return 0;
}
