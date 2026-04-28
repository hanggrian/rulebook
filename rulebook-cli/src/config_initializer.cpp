#include <algorithm>
#include <cmrc/cmrc.hpp>
#include <fstream>
#include <iostream>
#include "cli.h"
#include "config_initializer.h"
#include "linter/all.h"

CMRC_DECLARE(resources);

using namespace cmrc;
using namespace std;
using namespace std::filesystem;

int init_config(
    const string &linter,
    const optional<string> &dir,
    const bool google_variant,
    const bool silent
) {
    const optional<reference_wrapper<const Linter> > &optional_linter = get_linter_by_name(linter);
    if (!optional_linter.has_value()) {
        return die("Unknown linter '" + linter + "'.");
    }
    const Linter &actual_linter = *optional_linter;
    const path &target_dir = dir.has_value() ? path(*dir) : current_path();
    if (!is_directory(target_dir)) {
        return die("Not a directory.");
    }
    const path &target_path = target_dir / actual_linter.config_path;

    if (!silent) {
        cout << "\U0001f4e6 Linter:    " << BOLD << actual_linter.name << RESET << endl;
        cout << "\U0001f4c1 Directory: " << BOLD << target_dir.string() << RESET << endl;
        cout <<
                "\U0001f36d Flavor:    " <<
                BOLD <<
                (google_variant ? "google" : "default") <<
                RESET <<
                endl;
        cout << "\U0001f3af Target:    " << BOLD << target_path.string() << RESET << endl << endl;
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
    actual_linter.init(target_path, google_variant);

    cout << GREEN << "Done." << RESET << endl << endl;
    cout << "Goodbye!" << endl;
    return 0;
}
