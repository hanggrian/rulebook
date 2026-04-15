#ifndef CONFIG_INITIALIZER_H
#define CONFIG_INITIALIZER_H

#include <filesystem>
#include <string>

int init_config(
    const std::string &linter_name,
    const std::filesystem::path &target_dir,
    bool google_variant,
    bool verbose
);

#endif
