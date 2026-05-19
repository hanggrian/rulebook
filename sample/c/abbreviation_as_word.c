#include <stdio.h>

struct RestApi {
    int a;
    double b;
};

static void abbreviation_as_word(const struct RestApi api) {
    printf("%d%f", api.a, api.b);
}

int main() {
    struct RestApi api = { 1, 2.0 };
    abbreviation_as_word(api);
    return 0;
}
