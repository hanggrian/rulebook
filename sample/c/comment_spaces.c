#include <stdio.h>

struct RestApi {
    // Hello
    int a;
    double b; // World
};

void comment_spaces(const struct RestApi api) {
    printf("%d%f", api.a, api.b);
}
