#include <iostream>
#include <string>
#include "colors.hpp"

int die(const string &msg) {
    cerr << RED << msg << RESET << endl;
    return 1;
}

void warn(const string &msg) {
    cerr << YELLOW << msg << RESET << endl;
}

bool is_installed(const string &program) {
    return system(("which " + program + " > /dev/null 2>&1").c_str()) != 0;
}
