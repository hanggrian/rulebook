#include <stdio.h>

typedef struct Node {
    struct Node (*plus)();
} Node;

Node plus(int n) {
    Node n;
    n.plus = &plus;
    return n;
}

int main() {
    // Missing newline before second and fourth '.'
    Node foo =
        plus(0).plus(1)
            .plus(2).plus(3);

    // Valid wrapping
    Node bar =
        plus(
            0
        ).plus(1).plus(2).plus(3);
    Node baz =
        plus(0).plus(1).plus(2).plus(
            3
        );

    return 0;
}
