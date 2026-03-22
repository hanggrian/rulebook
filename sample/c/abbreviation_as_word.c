#include <stdio.h>

struct RestApi {
    int a;
    double b;
};

void abbreviation_as_word(const struct RestApi api) {
    printf("%d%f", api.a, api.b);
}
