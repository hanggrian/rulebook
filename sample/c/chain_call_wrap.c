#include <stdio.h>

typedef struct Node {
    struct Node (*plus)();
} Node;

Node plus() {
    Node n;
    n.plus = &plus;
    return n;
}

int main() {
    // Missing newline before second and fourth '.'
    Node foo =
        plus().plus()
            .plus().plus();

    // Valid wrapping
    Node bar = plus()
        .plus()
        .plus();

    return 0;
}
