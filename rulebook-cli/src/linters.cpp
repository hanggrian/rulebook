#include <string>
#include "linters.h"

using namespace cmrc;
using namespace std;

const Linter &get_linter_by_name(const string &name) {
    if (name == "checkstyle") {
        return CHECKSTYLE;
    }
    if (name == "codenarc") {
        return CODENARC;
    }
    if (name == "cppcheck") {
        return CPPCHECK;
    }
    if (name == "eslint") {
        return ESLINT;
    }
    if (name == "ktlint") {
        return KTLINT;
    }
    if (name == "pylint") {
        return PYLINT;
    }
    return TYPESCRIPTESLINT;
}

const Linter &get_linter_by_extension(const string &extension) {
    if (extension == ".c" || extension == ".cpp") {
        return CPPCHECK;
    }
    if (extension == ".java") {
        return CHECKSTYLE;
    }
    return KTLINT;
}
