#ifndef CONFIG_INITIALIZER_H
#define CONFIG_INITIALIZER_H

#include <filesystem>

int init_config(
    const std::string &linter,
    const std::optional<std::string> &dir,
    bool google_variant,
    bool verbose
);

#endif
