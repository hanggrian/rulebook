#include <stdexcept>

int illegal_throw(int a, int b) {
    if (a < 0 || b < 0) {
        throw std::overflow_error("received negative value");
    }
    return 0;
}

int main() {
    try {
        illegal_throw(-1, 0);
    } catch (const std::overflow_error&) {
        return 1;
    }
    return 0;
}
