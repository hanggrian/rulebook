#include <stdio.h>

struct ApiManag {
    int a;
    double b;
};

struct ApiManag api_manag;

void meaningless_word(const struct ApiManag api) {
    printf("%d%f", api.a, api.b);
}
