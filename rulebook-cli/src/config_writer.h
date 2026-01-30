#ifndef CONFIG_WRITER_H
#define CONFIG_WRITER_H

#include <string>

using namespace filesystem;
using namespace std;

int write(const string &linter, const path &dir, bool google);

#endif
