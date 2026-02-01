#ifndef CONFIG_WRITER_H
#define CONFIG_WRITER_H

#include <filesystem>
#include <string>

using namespace std::filesystem;
using namespace std;

int write(const string &linter, const path &dir, bool google);

#endif
