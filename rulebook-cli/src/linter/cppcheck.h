#ifndef CPPCHECK_H
#define CPPCHECK_H

#include "../cli.h"
#include "../files.h"
#include "base.h"

class CppcheckLinter : public Linter {
public:
    CppcheckLinter() {
        name = "cppcheck";
        default_config = "cppcheck_core.json";
        google_config = "cppcheck_google.json";
        config_path = "addon.json";
        file_extensions = std::list<std::string>{".c", ".cpp", ".h", ".hpp"};
    }

    [[nodiscard]] int print(const std::filesystem::path &target) const override {
        check_installed();
        const auto dump_file = std::filesystem::path(target.string() + ".dump");
        const int result = system(("cppcheck --dump " + target.string()).c_str());
        const std::ifstream dump(dump_file);
        std::cout << dump.rdbuf();
        remove(dump_file);
        return result;
    }

    [[nodiscard]] int lint(
        const std::filesystem::path &target,
        const bool google_variant
    ) const override {
        check_installed();
        std::string command = "cppheck ";
        command += "--enable=all ";
        command += "--check-level=exhaustive ";
        command += " --addon=";
        return system(
            (
                command +
                extract_resource(get_config(google_variant)).string() +
                " " +
                target.string()
            ).c_str()
        );
    }

    static void check_installed() {
        if (!is_installed("cppcheck")) {
            throw std::runtime_error("Missing Cppcheck.");
        }
    }
};

const CppcheckLinter CPPCHECK;

#endif
