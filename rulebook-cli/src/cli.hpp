#ifndef CLI_H
#define CLI_H

#include <string>

using namespace std;

int die(const string &msg);

void warn(const string &msg);

bool is_installed(const string &program);

#endif
