#ifndef CODENARC_H
#define CODENARC_H

#include "base.h"

class CodenarcLinter : public Linter {
public:
    CodenarcLinter() {
        name = "codenarc";
        default_config = "codenarc.xml";
        google_config = std::nullopt;
        config_path = "config/codenarc/codenarc.xml";
        file_extensions = std::list<std::string>{".groovy", ".gradle"};
    }
};

const CodenarcLinter CODENARC;

#endif
