using namespace std;

void foo() {
    try {
        foo();
    } catch (const std::overflow_error &e) {} catch (const std::runtime_error &e) {}
}
