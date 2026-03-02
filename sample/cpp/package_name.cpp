#include <iostream>

namespace my_namespace {
    int x = 42;
}

void package_name() {
    std::cout << my_namespace::x;
}
