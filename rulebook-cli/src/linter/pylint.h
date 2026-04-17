#ifndef PYLINT_H
#define PYLINT_H

#include "base.h"

class PylintLinter : public Linter {
public:
    PylintLinter() {
        name = "pylint";
        default_config = "pylint_pylint";
        google_config = "pylint_google";
        config_path = ".pylintrc";
        file_extensions = std::list<std::string>{".py"};
    }
};

const PylintLinter PYLINT;

#endif
