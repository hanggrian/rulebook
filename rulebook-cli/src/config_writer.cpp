#include <algorithm>
#include <cmrc/cmrc.hpp>
#include <filesystem>
#include <fstream>
#include <iostream>
#include <string>
#include <vector>
#include "cli.h"
#include "colors.h"
#include "config_writer.h"

CMRC_DECLARE(resources);

using namespace filesystem;
using namespace std;
using namespace cmrc;

int write(const string &linter, const path &dir, bool google) {
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
    path target_file = dir;
    const embedded_filesystem fs = resources::get_filesystem();
    if (linter == "checkstyle") {
        source_file =
                fs.open(
                    !google
                        ? "resources/checkstyle_sun.xml"
                        : "resources/checkstyle_google.xml"
                );
        target_file /= "config/checkstyle/checkstyle.xml";
    } else if (linter == "cppcheck") {
        source_file =
                fs.open(
                    !google
                        ? "resources/cppcheck_google.json"
                        : "resources/cppcheck_qt.json"
                );
        target_file /= "addon.json";
    } else if (linter == "codenarc") {
        source_file = fs.open("resources/codenarc.xml");
        target_file /= "config/codenarc/codenarc.xml";
    } else if (linter == "eslint") {
        source_file =
                fs.open(
                    !google
                        ? "resources/eslint_proxmox.config.js"
                        : "resources/eslint_google.config.js"
                );
        target_file /= "eslint.config.js";
    } else if (linter == "ktlint") {
        source_file = fs.open("resources/ktlint.editorconfig");
        target_file /= "config/codenarc/codenarc.xml";
    } else if (linter == "pylint") {
        source_file =
                fs.open(
                    !google
                        ? "resources/pylint_pylint"
                        : "resources/pylint_google"
                );
        target_file /= ".pylintrc";
    } else if (linter == "typescript-eslint") {
        source_file =
                fs.open(
                    !google
                        ? "resources/typescript_eslint_microsoft.config.js"
                        : "resources/typescript_eslint_google.config.js"
                );
        target_file /= "eslint.config.js";
    }

    cout << "Linter: " << BOLD << linter << RESET << endl;
    cout << "Flavor: " << BOLD << (google ? "google" : "default") << RESET << endl;
    cout << "Config: " << BOLD << target_file << RESET << endl << endl;

    if (is_regular_file(target_file)) {
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
    if (path path(target_file); path.has_parent_path()) {
        create_directories(path.parent_path());
    }
    ofstream out(target_file, ios::binary);
    out.write(source_file.begin(), static_cast<streamsize>(source_file.size()));
    out.close();

    cout << GREEN << "Done." << RESET << endl << endl;
    cout << "Goodbye!" << endl;
    return 0;
}
