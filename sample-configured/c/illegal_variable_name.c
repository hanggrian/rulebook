#include <stdio.h>

struct Api {
  int foo2;
};

static void illegal_name(const struct Api api) {
  printf("%d", api.foo2);
}

int main() {
  struct Api api = { .foo2 = 42 };
  illegal_name(api);
  return 0;
}
