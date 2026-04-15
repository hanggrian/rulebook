#ifndef SOURCE_CHECKER_H
#define SOURCE_CHECKER_H

#include <filesystem>

int run_lint(const std::filesystem::path &target_path, bool google_variant, bool verbose);

#endif
