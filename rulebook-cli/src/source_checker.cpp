#include <algorithm>
#include <set>
#include <string>
#include "extraction.hpp"
#include "colors.hpp"
#include "source_checker.hpp"

#include <iostream>

using namespace std;

namespace {
    string get_binary(const path &path) {
        const string extension = path.extension().string();
        if (extension == ".c" || extension == ".cpp") {
            return "cppcheck";
        }
        if (extension == ".java") {
            return extract_binary("checkstyle.jar");
        }
        return extract_binary("ktlint");
    }
}

int check_source(const path &path, const bool verbose) {
    set<string> binaries = {};
    if (is_regular_file(path)) {
        binaries.insert(get_binary(path));
    } else {
        for (const auto &entry: recursive_directory_iterator(path)) {
            binaries.insert(get_binary(entry.path()));
        }
    }

    if (verbose) {
        cout << "Path:     " << BOLD << path.string() << RESET << endl;
        cout <<
                "Binaries: " <<
                BOLD <<
                ranges::fold_left(binaries, string{}, [](const string &a, const string &b) {
                    return a.empty() ? b : a + ", " + b;
                }) <<
                RESET <<
                endl <<
                endl;
    }

    string command;
    for (const string &binary: binaries) {
        if (binary.ends_with("checkstyle.jar")) {
            command =
                "java -cp " +
                extract_binary("rulebook-checkstyle.jar").string() +
                ":" +
                extract_binary("kotlin-stdlib.jar").string() +
                ":" +
                binary +
                " com.puppycrawl.tools.checkstyle.Main -c " +
                extract_resource("checkstyle_sun.xml").string() +
                " " +
                path.string();
            cout << command << endl;
        } else if (binary.ends_with("ktlint")) {
            command =
                    binary +
                    " -R " +
                    extract_binary("rulebook-ktlint.jar").string() +
                    " " +
                    path.string();
        }
    }
    return system(command.c_str());
}
