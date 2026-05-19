#include <stdio.h>

static int line_length() {
    const char *foo = "                                                                           ";
    printf("%s", foo);
    return 0;
}

int main() {
    line_length();
    return 0;
}
