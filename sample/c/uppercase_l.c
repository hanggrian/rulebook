#include <stdio.h>

void uppercase_l() {
    constexpr long foo = 1L;
    constexpr long long bar = 1LL;

    printf("%ld", foo);
    printf("%lld", bar);
}
