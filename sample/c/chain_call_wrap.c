#include <stdio.h>

typedef struct Node {
    struct Node (*plus)(int x);
} Node;

Node plus(int n) {
    Node node;
    node.plus = &plus;
    return node;
}

int main() {
    // Missing newline before second and fourth '.'
    const Node foo =
        plus(0)
            .plus(1)
            .plus(2)
            .plus(3);

    // Valid wrapping
    const Node bar =
        plus(
            0
        ).plus(1)
            .plus(2)
            .plus(3);
    const Node baz =
        plus(0)
            .plus(1)
            .plus(2)
            .plus(
                3
            );

    foo.plus(4);
    bar.plus(5);
    baz.plus(6);

    return 0;
}
