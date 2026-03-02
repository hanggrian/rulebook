#include <stdio.h>

int main() {
    constexpr long foo = 1L;
    constexpr long long bar = 1LL;

    printf("%ld", foo);
    printf("%lld", bar);
}
