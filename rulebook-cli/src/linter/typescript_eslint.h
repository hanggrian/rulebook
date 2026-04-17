#ifndef TYPESCRIPT_ESLINT_H
#define TYPESCRIPT_ESLINT_H

#include "base.h"

class TypescriptEslintLinter : public Linter {
public:
    TypescriptEslintLinter() {
        name = "typescript-eslint";
        default_config = "typescript_eslint_crockford.config.js";
        google_config = "typescript_eslint_google.config.js";
        config_path = "eslint.config.js";
        file_extensions = std::list<std::string>{".ts", ".tsx"};
    }
};

const TypescriptEslintLinter TYPESCRIPT_ESLINT;

#endif
