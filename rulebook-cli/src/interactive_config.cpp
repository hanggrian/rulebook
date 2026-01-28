#include <algorithm>
#include <iostream>
#include <string>
#include <numeric>
#include <vector>
#include "color.h"
#include "interactive_config.h"

using namespace colors;
using namespace std;

const vector<string> CHECKSTYLE_FLAVORS = {"sun", "google"};
const vector<string> CPPCHECK_FLAVORS = {"qt", "google"};
const vector<string> PYLINT_FLAVORS = {"pylint", "google"};
const vector<string> JAVASCRIPT_FLAVORS = {"proxmox", "google"};
const vector<string> TYPESCRIPT_FLAVORS = {"microsoft", "google"};

namespace InteractiveConfig {
    void start(const string &linter) {
        cout << "Type 'exit' to cancel configuration." << endl << endl;

        string flavor;
        while (flavor.empty()) {
            vector<string> available_flavors;
            if (flavor.empty()) {
                if (linter == "checkstyle") {
                    available_flavors = CHECKSTYLE_FLAVORS;
                } else if (linter == "codenarc") {
                    flavor = "groovy";
                    break;
                } else if (linter == "ktlint") {
                    flavor = "ktlint";
                    break;
                } else if (linter == "cppcheck") {
                    available_flavors = CPPCHECK_FLAVORS;
                } else if (linter == "pylint") {
                    available_flavors = PYLINT_FLAVORS;
                } else if (linter == "eslint") {
                    available_flavors = JAVASCRIPT_FLAVORS;
                } else {
                    available_flavors = TYPESCRIPT_FLAVORS;
                }
                cout <<
                        yellow() <<
                        "Flavor (" <<
                        accumulate(
                            next(available_flavors.begin()),
                            available_flavors.end(),
                            available_flavors[0],
                            [](const string &a, const string &b) { return (a + "/").append(b); }
                        ) <<
                        "): " <<
                        reset();
            }
            string input;
            if (!getline(cin, input) || input == "exit" || input == "e") {
                cout << "Configuration canceled.";
                return;
            }
            if (ranges::transform(input, input.begin(), ::tolower);
                ranges::find(available_flavors, input) !=
                available_flavors.end()) {
                flavor = input;
            }
        }
        cout << green() << "Done." << reset() << endl << endl;
        cout << "Goodbye!" << endl;
    }
}
