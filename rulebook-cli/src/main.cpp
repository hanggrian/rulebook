#include <iostream>
#include <filesystem>
#include <numeric>
#include <string>
#include <vector>
#include <boost/program_options.hpp>
#include "color.h"
#include "interactive_config.h"

using namespace colors;
using namespace std::filesystem;
using namespace std;
using namespace boost::program_options;

const string APP_VERSION = "0.3";
const string OPTIONS_TITLE = bold() + yellow() + "Options" + reset();
const string CONFIG_MESSAGE = "Produces linter-specific configuration file";
const string PRINT_MESSAGE = "Show AST output of input file";

const vector<string> CONFIG_LINTERS = {
    "cppcheck",
    "checkstyle",
    "codenarc",
    "eslint",
    "ktlint",
    "pylint",
    "typescript-eslint"
};
const vector<string> PRINT_FILES = {
    "c",
    "cpp",
    "groovy",
    "java",
    "js",
    "jsx",
    "kotlin",
    "ts",
    "tsx",
    "py"
};

int die(const string &msg) {
    cerr << red() << msg << reset() << endl;
    return 1;
}

bool is_config(const string &command) {
    return command == "config" || command == "c";
}

bool is_print(const string &command) {
    return command == "print" || command == "p";
}

int main(const int argc, char **argv) {
    if (argc < 2) {
        return die("See --help.");
    }
    const string command = argv[1];

    options_description opts(OPTIONS_TITLE);
    opts.add_options()
            ((yellow() + "version" + reset() + ",v").c_str(), "Show app version")
            ((yellow() + "help" + reset() + ",h").c_str(), "Display this message");

    options_description config_opts(OPTIONS_TITLE);
    config_opts.add_options()
            ((yellow() + "help" + reset() + ",h").c_str(), "Display this message");
    options_description config_opts_hidden("");
    config_opts_hidden.add_options()
            ("linter", value<string>(), "Linter library name");
    options_description config_opts_all;
    config_opts_all.add(config_opts).add(config_opts_hidden);

    options_description print_opts(OPTIONS_TITLE);
    print_opts.add_options()
            ((yellow() + "help" + reset() + ",h").c_str(), "Display this message");
    options_description print_opts_hidden("");
    print_opts_hidden.add_options()
            ("file", value<string>(), "Source code file");
    options_description print_opts_all;
    print_opts_all.add(print_opts).add(print_opts_hidden);

    if (is_config(command) || is_print(command)) {
        const int command_argc = argc - 1;
        char **command_argv = argv + 1;
        string help_msg;
        string help_msg_ext =
                bold() +
                green() +
                "Usage" +
                reset() +
                ":\n  " +
                green() +
                "rulebook " +
                command +
                reset() +
                " " +
                cyan() +
                "<";
        const options_description *help_opt;
        const options_description *help_opt_all;
        string target;
        string target_val;
        if (is_config(command)) {
            help_msg = CONFIG_MESSAGE;
            help_opt = &config_opts;
            help_opt_all = &config_opts_all;
            target = "linter";
        } else {
            help_msg = PRINT_MESSAGE;
            help_opt = &print_opts;
            help_opt_all = &print_opts_all;
            target = "file";
        }
        help_msg_ext +=
                target +
                ">" +
                reset() +
                " " +
                yellow() +
                "[options]" +
                reset() +
                "\n\n" +
                (
                    is_config(command)
                        ? (
                            bold() +
                            cyan() +
                            "Linter" +
                            reset() +
                            ": [" +
                            accumulate(
                                CONFIG_LINTERS.begin(),
                                CONFIG_LINTERS.end(),
                                string(""),
                                [](const string &a, const string &b) {
                                    const string b_colored = cyan() + b + reset();
                                    return a.empty() ? b_colored : (a + "," + b_colored);
                                }) +
                            "]"
                        )
                        : (
                            bold() +
                            cyan() +
                            "File" +
                            reset() +
                            ": *.{" +
                            accumulate(
                                PRINT_FILES.begin(),
                                PRINT_FILES.end(),
                                string(""),
                                [](const string &a, const string &b) {
                                    const string b_colored = cyan() + b + reset();
                                    return a.empty() ? b_colored : (a + "," + b_colored);
                                }) +
                            "}"
                        )
                ) +
                "\n\n";

        for (int i = 1; i < command_argc; ++i) {
            if (string(command_argv[i]) != "--help") {
                continue;
            }
            cout << help_msg << "." << endl << endl;
            cout << help_msg_ext;
            cout << *help_opt;
            return 0;
        }
        try {
            variables_map variables;
            positional_options_description pos;
            pos.add(target.c_str(), 1);
            store(
                command_line_parser(command_argc, command_argv)
                .options(*help_opt_all)
                .positional(pos)
                .run(),
                variables
            );
            if (variables.contains(yellow() + "help" + reset())) {
                cout << help_msg << "." << endl << endl;
                cout << help_msg_ext;
                cout << *help_opt;
                return 0;
            }
            notify(variables);
            if (!variables.contains(target)) {
                return die("Missing " + target + ".");
            }
            target_val = variables[target].as<string>();
        } catch (exception &e) {
            return die(e.what());
        }

        if (is_config(command)) {
            if (ranges::transform(target_val, target_val.begin(), ::tolower);
                ranges::find(CONFIG_LINTERS, target_val) ==
                CONFIG_LINTERS.end()) {
                return die("Unknown linter '" + target_val + "'.");
            }
            InteractiveConfig::start(target_val);
        } else {
            if (!exists(target_val) || !is_regular_file(target_val)) {
                return die("File not found.");
            }
            // TODO link Checkstyle and Ktlint binaries
        }
        return 0;
    }
    if (command == "--version" || command == "-v") {
        cout << bold() << APP_VERSION << reset() << endl;
        return 0;
    }
    if (command == "--help" || command == "-h") {
        cout << "A simple tool for linting." << endl << endl;
        cout <<
                bold() <<
                green() <<
                "Usage" <<
                reset() <<
                ":" <<
                endl <<
                "  " <<
                green() <<
                "rulebook" <<
                reset() <<
                " " <<
                cyan() <<
                "<command>" <<
                reset() <<
                " " <<
                yellow() <<
                "[options]" <<
                reset() <<
                endl <<
                endl;
        cout << bold() << cyan() << "Commands" << reset() << ":" << endl;
        cout << cyan() << "  config  " << reset() << CONFIG_MESSAGE << endl;
        cout << cyan() << "  print   " << reset() << PRINT_MESSAGE << endl;
        cout << endl << opts;
        return 0;
    }
    return die("Unknown command '" + command + "'.");
}
