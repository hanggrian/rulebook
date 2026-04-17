#ifndef CHECKSTYLE_H
#define CHECKSTYLE_H

#include "base.h"
#include "../cli.h"
#include "../extraction.h"

class CheckstyleLinter : public Linter {
public:
    CheckstyleLinter() {
        name = "checkstyle";
        default_config = "checkstyle_sun.xml";
        google_config = "checkstyle_google.xml";
        config_path = "config/checkstyle/checkstyle.xml";
        file_extensions = std::list<std::string>{".java"};
    }

    [[nodiscard]] int print(const std::filesystem::path &target) const override {
        check_installed();
        const std::string binary = extract_binary("checkstyle.jar").string();
        const std::string target_value = target.string();
        return system(
            (
                "java -jar " +
                binary +
                " -T " +
                target_value +
                " && java -jar " +
                binary +
                " -J " +
                target_value
            ).c_str()
        );
    }

    [[nodiscard]] int lint(
        const std::filesystem::path &target,
        const bool google_variant
    ) const override {
        check_installed();
        return system(
            (
                "java -cp " +
                extract_binary("rulebook-checkstyle.jar").string() +
                ":" +
                extract_binary("kotlin-stdlib.jar").string() +
                ":" +
                extract_binary("checkstyle.jar").string() +
                " com.puppycrawl.tools.checkstyle.Main -c " +
                extract_resource(get_config(google_variant)).string() +
                " " +
                target.string()
            ).c_str()
        );
    }

    static void check_installed() {
        if (!is_installed("java")) {
            throw std::runtime_error("Missing JRE or JDK.");
        }
    }
};

const CheckstyleLinter CHECKSTYLE;

#endif
