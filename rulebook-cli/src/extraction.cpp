#include <cmrc/cmrc.hpp>
#include <fstream>
#include "checkstyle_bin.h"
#include "cli.hpp"
#include "extraction.hpp"
#include "kotlin_stdlib_bin.h"
#include "ktlint_bin.h"
#include "ktlint_print_bin.h"
#include "rulebook_checkstyle_bin.h"
#include "rulebook_ktlint_bin.h"

CMRC_DECLARE(resources);

using namespace cmrc;

path extract_binary(const string &filename) {
    const path temp_file = temp_directory_path() / filename;
    if (!exists(temp_file)) {
        ofstream out(temp_file, ios::binary);
        if (filename == "ktlint") {
            out.write(
                reinterpret_cast<const char *>(ktlint),
                ktlint_len
            );
            permissions(temp_file, perms::owner_exec | perms::owner_read | perms::owner_write);
        } else if (filename == "ktlint_print") {
            out.write(
                reinterpret_cast<const char *>(ktlint_print),
                ktlint_print_len
            );
            permissions(temp_file, perms::owner_exec | perms::owner_read | perms::owner_write);
        } else if (filename == "checkstyle.jar") {
            out.write(
                reinterpret_cast<const char *>(checkstyle_jar),
                checkstyle_jar_len
            );
        } else if (filename == "kotlin-stdlib.jar") {
            out.write(
                reinterpret_cast<const char *>(kotlin_stdlib_jar),
                kotlin_stdlib_jar_len
            );
        } else if (filename == "rulebook-checkstyle.jar") {
            out.write(
                reinterpret_cast<const char *>(rulebook_checkstyle_jar),
                rulebook_checkstyle_jar_len
            );
        } else {
            out.write(
                reinterpret_cast<const char *>(rulebook_ktlint_jar),
                rulebook_ktlint_jar_len
            );
        }
        warn("Extracted '" + filename + "' to temporary folder.");
    }
    return temp_file;
}

path extract_resource(const string &filename) {
    const path temp_file = temp_directory_path() / filename;
    if (!exists(temp_file)) {
        const auto file = resources::get_filesystem().open("resources/" + filename);
        ofstream out(temp_file, ios::binary);
        out.write(file.begin(), static_cast<streamsize>(file.size()));
        warn("Extracted '" + filename + "' to temporary folder.");
    }
    return temp_file;
}
