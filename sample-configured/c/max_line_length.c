#include <stdio.h>

static int line_length() {
  const char *foo2 = "                         ";
  printf("%s", foo2);
  return 0;
}

int main() {
  line_length();
  return 0;
}
