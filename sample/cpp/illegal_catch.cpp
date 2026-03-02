#include <system_error>

void foo() {
    if (true) {
    } else if (false) {
    } else {
    }

    if (false) {}

    do {} while (true);

    try {
        foo();
    } catch (const std::overflow_error &e) {
    } catch (const std::runtime_error &e) {
    }
}
