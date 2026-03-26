#ifndef SOURCE_CHECKER_H
#define SOURCE_CHECKER_H

#include <filesystem>

using namespace std::filesystem;

int run_lint(const path &target_path, bool google_variant, bool verbose);

#endif
