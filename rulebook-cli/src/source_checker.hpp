#ifndef SOURCE_CHECKER_H
#define SOURCE_CHECKER_H

#include <filesystem>

using namespace std::filesystem;

int check_source(const path &path, bool verbose);

#endif
