#include <stdio.h>

int main() {
    constexpr int foo =
        0 +
        1;
    constexpr bool bar =
        false +
        false +
        true;

    printf("%d %d", foo, bar);
}
