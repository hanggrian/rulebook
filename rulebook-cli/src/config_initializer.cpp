#include <algorithm>
#include <cmrc/cmrc.hpp>
#include <fstream>
#include <iostream>
#include "cli.hpp"
#include "colors.hpp"
#include "config_initializer.hpp"

CMRC_DECLARE(resources);

using namespace cmrc;

int init_config(const string &linter, const path &dir, bool google, bool verbose) {
    const vector<string> linters_with_flavors = {
        "checkstyle",
        "cppcheck",
        "eslint",
        "pylint",
        "typescript-eslint",
    };
    if (google && ranges::find(linters_with_flavors, linter) == linters_with_flavors.end()) {
        return die("Google style guide unavailable for this linter.");
    }

    file source_file;
    path target_path = dir;
    const embedded_filesystem fs = resources::get_filesystem();
    if (linter == "checkstyle") {
        source_file =
                fs.open(
                    !google
                        ? "resources/checkstyle_sun.xml"
                        : "resources/checkstyle_google.xml"
                );
        target_path /= "config/checkstyle/checkstyle.xml";
    } else if (linter == "cppcheck") {
        source_file =
                fs.open(
                    !google
                        ? "resources/cppcheck_core.json"
                        : "resources/cppcheck_google.json"
                );
        target_path /= "addon.json";
    } else if (linter == "codenarc") {
        source_file = fs.open("resources/codenarc.xml");
        target_path /= "config/codenarc/codenarc.xml";
    } else if (linter == "eslint") {
        source_file =
                fs.open(
                    !google
                        ? "resources/eslint_crockford.config.js"
                        : "resources/eslint_google.config.js"
                );
        target_path /= "eslint.config.js";
    } else if (linter == "ktlint") {
        source_file = fs.open("resources/ktlint.editorconfig");
        target_path /= ".editorconfig";
    } else if (linter == "pylint") {
        source_file =
                fs.open(
                    !google
                        ? "resources/pylint_pylint"
                        : "resources/pylint_google"
                );
        target_path /= ".pylintrc";
    } else {
        source_file =
                fs.open(
                    !google
                        ? "resources/typescript_eslint_crockford.config.js"
                        : "resources/typescript_eslint_google.config.js"
                );
        target_path /= "eslint.config.js";
    }

    if (verbose) {
        cout << "Linter:    " << BOLD << linter << RESET << endl;
        cout << "Directory: " << BOLD << dir.string() << RESET << endl;
        cout << "Flavor:    " << BOLD << (google ? "google" : "default") << RESET << endl;
        cout << "Config:    " << BOLD << target_path.string() << RESET << endl << endl;
    }

    if (is_regular_file(target_path)) {
        cout << "File already exists." << endl;
        while (true) {
            cout << YELLOW << "Overwrite (yes/no)? " << RESET;
            string input;
            getline(cin, input);
            ranges::transform(input, input.begin(), ::tolower);
            if (input == "n" || input == "no") {
                return die("Configuration canceled.");
            }
            if (input.empty() || input == "y" || input == "yes") {
                break;
            }
        }
    }
    create_directories(target_path.parent_path());
    ofstream stream(target_path, ios::binary);
    stream.write(source_file.begin(), static_cast<streamsize>(source_file.size()));
    stream.close();

    cout << GREEN << "Done." << RESET << endl << endl;
    cout << "Goodbye!" << endl;
    return 0;
}
