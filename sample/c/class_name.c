#include <stdio.h>

struct RestApi {
    int a;
    double b;
};

void class_name(const struct RestApi api) {
    printf("%d%f", api.a, api.b);
}
