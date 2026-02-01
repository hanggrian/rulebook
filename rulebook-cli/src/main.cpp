#include <boost/program_options.hpp>
#include <filesystem>
#include <iostream>
#include <numeric>
#include <string>
#include <vector>
#include "cli.h"
#include "colors.h"
#include "config_writer.h"

using namespace boost::program_options;
using namespace filesystem;
using namespace std;

constexpr string APP_VERSION = "0.3";

int main(const int argc, char **argv) {
    if (argc < 2) {
        return die("See --help.");
    }

    options_description options(BOLD + YELLOW + "Options" + RESET);
    options.add_options()
            ("google,g", bool_switch()->default_value(false), "Use Google style guide")
            ("version,v", "Show app version")
            ("help,h", "Display this message");
    variables_map variables;
    try {
        store(parse_command_line(argc, argv, options), variables);
        notify(variables);
    } catch (exception &e) {
        return die(e.what());
    }

    if (variables.contains("version")) {
        cout << BOLD << APP_VERSION << RESET << endl;
        return 0;
    }
    if (variables.contains("help")) {
        cout << "Produces linter-specific configuration file" << endl << endl;
        cout <<
                BOLD <<
                GREEN <<
                "Usage" <<
                RESET <<
                ":" <<
                endl <<
                "  rulebook " <<
                CYAN <<
                "<linter>" <<
                RESET <<
                " " <<
                RED <<
                "<dir>" <<
                RESET <<
                " " <<
                YELLOW <<
                "[options]" <<
                RESET <<
                endl <<
                endl;
        cout <<
                BOLD <<
                CYAN <<
                "Linter" <<
                RESET <<
                ":" <<
                endl <<
                "  cppcheck           Core guidelines" <<
                endl <<
                "  checkstyle         Sun style" <<
                endl <<
                "  codenarc           Groovy style guide" <<
                endl <<
                "  eslint             Crockford code conventions" <<
                endl <<
                "  ktlint             Ktlint official style" <<
                endl <<
                "  python             Pylint style" <<
                endl <<
                "  typescript-eslint  Crockford code conventions" <<
                endl <<
                endl;
        cout <<
                BOLD <<
                RED <<
                "Directory" <<
                RESET <<
                ":" <<
                endl <<
                "  Project dir, or current path" <<
                endl <<
                endl;
        cout << options;
        return 0;
    }
    const vector<string> available_linters = {
        "checkstyle",
        "cppcheck",
        "codenarc",
        "eslint",
        "ktlint",
        "pylint",
        "typescript-eslint",
    };
    const string linter = argv[1];
    string linter_lower = linter;
    if (ranges::transform(linter_lower, linter_lower.begin(), ::tolower);
        ranges::find(available_linters, linter_lower) ==
        available_linters.end()) {
        return die("Unknown linter '" + linter + "'.");
    }
    const path dir = (argc > 2) ? path(argv[2]) : current_path();
    if (!is_directory(dir)) {
        return die("Not a directory.");
    }
    return write(linter, dir, variables["google"].as<bool>());
}
