#include <stdio.h>

struct ApiManag {
    int a;
    double b;
};

struct ApiManag api_manag;

static void meaningless_word(const struct ApiManag api) {
    printf("%d%f", api.a, api.b);
}

int main() {
    api_manag.a = 1;
    api_manag.b = 2.0;
    meaningless_word(api_manag);
    return 0;
}
