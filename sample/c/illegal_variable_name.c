#include <stdio.h>

struct Api {
    int intege;
};

static void illegal_variable_name(const struct Api api) {
    printf("%d", api.intege);
}

int main() {
    struct Api api = { .intege = 42 };
    illegal_variable_name(api);
    return 0;
}
