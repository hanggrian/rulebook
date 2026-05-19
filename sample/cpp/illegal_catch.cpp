#include <stdexcept>

void illegal_catch() {
    if (true) {
    } else if (false) {
    } else {
    }

    if (false) {}

    do {} while (true);

    try {
        illegal_catch();
    } catch (const std::overflow_error &e) {
    } catch (const std::runtime_error &e) {
    }
}

int main() {
    illegal_catch();
    return 0;
}
