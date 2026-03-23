#ifndef CONFIG_INITIALIZER_H
#define CONFIG_INITIALIZER_H

#include <filesystem>
#include <string>

using namespace std;
using namespace std::filesystem;

int init_config(const string &linter, const path &dir, bool google, bool verbose);

#endif
