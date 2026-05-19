#include <stdio.h>

static void lowercase_hexadecimal() {
    constexpr int foo = 0xaabbcc;
    printf("%d", foo);
}

int main() {
    lowercase_hexadecimal();
    return 0;
}
