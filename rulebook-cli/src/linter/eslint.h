#ifndef ESLINT_H
#define ESLINT_H

#include "base.h"

class EslintLinter : public Linter {
public:
    EslintLinter() {
        name = "eslint";
        default_config = "eslint_crockford.config.js";
        google_config = "eslint_google.config.js";
        config_path = "eslint.config.js";
        file_extensions = std::list<std::string>{".js", ".jsx"};
    }
};

const EslintLinter ESLINT;

#endif
