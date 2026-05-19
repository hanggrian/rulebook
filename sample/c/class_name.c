#include <stdio.h>

struct RestApi {
    int a;
    double b;
};

static void class_name(const struct RestApi api) {
    printf("%d%f", api.a, api.b);
}

int main() {
    struct RestApi api = { 1, 2.0 };
    class_name(api);
    return 0;
}
