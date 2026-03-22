#include <stdio.h>

void assignment_wrap() {
    constexpr int foo =
        0 +
        1;
    constexpr int bar = 1;
    const int baz[2] = {
        1,
        2
    };
    printf("%d %d", foo, bar);
    for (int i = 0; i < 2; i++) {
        printf("%d\n", baz[i]);
    }
}
