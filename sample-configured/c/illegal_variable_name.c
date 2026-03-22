#include <stdio.h>

struct Api {
  int foo2;
};

void illegal_variable_name(const struct Api api) {
  printf("%d", api.foo2);
}
