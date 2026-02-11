#include <iostream>
#include <string>
#include "colors.h"

using namespace std;

int die(const string &msg) {
    cerr << RED << msg << RESET << endl;
    return 1;
}
