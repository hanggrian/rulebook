#include <stdio.h>

struct Api {
    int intege;
};

void illegal_variable_name(const struct Api api) {
    printf("%d", api.intege);
}
