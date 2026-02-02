#include <iostream>
#include <string>
#include "colors.h"

using namespace std;

int die(const string &msg) {
    cerr << RED << msg << RESET << endl;
    return 1;
}

void foo() {
    try {
        foo();
    } catch (const std::overflow_error &e) {
    } catch (const std::runtime_error &e) {
    } catch (const std::exception &e) {
    }
}
