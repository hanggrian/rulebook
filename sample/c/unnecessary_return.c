#include <stdio.h>

static void unnecessary_return() {
    printf("foo");
    // return;
}

int main() {
    unnecessary_return();
    return 0;
}
