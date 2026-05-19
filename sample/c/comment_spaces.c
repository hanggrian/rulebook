#include <stdio.h>

struct RestApi {
    // Hello
    int a;
    double b; // World
};

static void comment_spaces(const struct RestApi api) {
    printf("%d%f", api.a, api.b);
}

int main() {
    struct RestApi api = { 1, 2.0 };
    comment_spaces(api);
    return 0;
}
