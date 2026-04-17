#ifndef ALL_H
#define ALL_H

#include <cmrc/cmrc.hpp>
#include "base.h"
#include "checkstyle.h"
#include "codenarc.h"
#include "cppcheck.h"
#include "eslint.h"
#include "ktlint.h"
#include "pylint.h"
#include "typescript_eslint.h"

CMRC_DECLARE(resources);

const std::list<std::reference_wrapper<const Linter>> ALL_LINTERS = {
    CHECKSTYLE,
    CODENARC,
    CPPCHECK,
    ESLINT,
    KTLINT,
    PYLINT,
    TYPESCRIPT_ESLINT,
};

std::optional<std::reference_wrapper<const Linter>> get_linter_by_name(
    const std::string &name
);

std::optional<std::reference_wrapper<const Linter>> get_linter_by_extension(
    const std::string &extension
);

#endif
