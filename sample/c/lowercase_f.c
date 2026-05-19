#include <stdio.h>

static void lowercase_f() {
    constexpr float foo = 1.0f;
    printf("%f", foo);
}

int main() {
    lowercase_f();
    return 0;
}
