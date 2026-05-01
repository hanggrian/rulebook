#ifndef KTLINT_H
#define KTLINT_H

#include "../files.h"
#include "base.h"

class KtlintLinter : public Linter {
public:
    KtlintLinter() {
        name = "ktlint";
        default_config = "ktlint.editorconfig";
        google_config = std::nullopt;
        config_path = ".editorconfig";
        file_extensions = std::list<std::string>{".kt", ".kts"};
    }

    [[nodiscard]] int print(const std::filesystem::path &target) const override {
        return system(
            (extract_binary("ktlint_print").string() + " --color printAST " + target.string())
            .c_str()
        );
    }

    [[nodiscard]] int lint(const std::filesystem::path &target, const bool _) const override {
        return system(
            (
                extract_binary("ktlint").string() +
                " -R " +
                extract_binary("rulebook-ktlint.jar").string() +
                " " +
                target.string()
            ).c_str()
        );
    }
};

const KtlintLinter KTLINT;

#endif
