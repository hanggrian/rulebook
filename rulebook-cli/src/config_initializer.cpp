#include <algorithm>
#include <cmrc/cmrc.hpp>
#include <fstream>
#include <iostream>
#include "cli.hpp"
#include "config_initializer.hpp"
#include "linters.hpp"

CMRC_DECLARE(resources);

using namespace cmrc;

int init_config(
    const string &linter_name,
    const path &target_dir,
    const bool google_variant,
    const bool verbose
) {
    const Linter &linter = get_linter_by_name(linter_name);
    const path target_path = target_dir / linter.relative_path;

    if (verbose) {
        cout << "Linter:    " << BOLD << linter.name << RESET << endl;
        cout << "Directory: " << BOLD << target_dir.string() << RESET << endl;
        cout << "Flavor:    " << BOLD << (google_variant ? "google" : "default") << RESET << endl;
        cout << "Target:    " << BOLD << target_path.string() << RESET << endl << endl;
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
    linter.init(target_path, google_variant);

    cout << GREEN << "Done." << RESET << endl << endl;
    cout << "Goodbye!" << endl;
    return 0;
}
