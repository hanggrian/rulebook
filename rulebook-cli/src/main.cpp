#include <boost/program_options.hpp>
#include <filesystem>
#include <iostream>
#include <numeric>
#include <string>
#include <vector>
#include "cli.hpp"
#include "colors.hpp"
#include "config_initializer.hpp"
#include "file_printer.hpp"
#include "source_checker.hpp"

using namespace boost::program_options;
using namespace filesystem;
using namespace std;

namespace {
    const vector<string> SUPPORTED_EXTENSIONS = {
        ".c",
        ".cpp",
        ".java",
        ".kt",
        ".kts",
    };
    const vector<string> AVAILABLE_LINTERS = {
        "checkstyle",
        "cppcheck",
        "codenarc",
        "eslint",
        "ktlint",
        "pylint",
        "typescript-eslint",
    };
}

int main(const int argc, char **argv) {
    if (argc < 2) {
        return die("See --help.");
    }

    options_description options(BOLD + BLUE + "Options" + RESET);
    options.add_options()
            ("google,G", bool_switch()->default_value(false), "Use Google style guide")
            ("help,h", "Display this message")
            ("verbose,v", bool_switch()->default_value(false), "Enable verbose output")
            ("version,V", "Show app version");
    options_description hidden;
    hidden.add_options()
            ("command", value<string>())
            ("subargs", value<vector<string> >());
    options_description all;
    all.add(options).add(hidden);

    positional_options_description positional;
    positional.add("command", 1);
    positional.add("subargs", -1);

    variables_map variables;
    parsed_options parsed =
            command_line_parser(argc, argv)
            .options(all)
            .positional(positional)
            .allow_unregistered()
            .run();
    try {
        store(parsed, variables);
        notify(variables);
    } catch (error &e) {
        return die(e.what());
    }

    if (variables.contains("version")) {
        cout << "rulebook " << BOLD << VERSION << RESET << endl;
        return 0;
    }
    if (variables.contains("help")) {
        cout << "Helper for Rulebook linter extensions" << endl << endl;
        cout <<
                BOLD <<
                "Usage" <<
                RESET <<
                ":" <<
                endl <<
                "  rulebook " <<
                CYAN <<
                "<command>" <<
                RESET <<
                " " <<
                MAGENTA <<
                "<arguments>" <<
                RESET <<
                " " <<
                BLUE <<
                "[options]" <<
                RESET <<
                endl <<
                endl;
        cout <<
                BOLD <<
                CYAN <<
                "Command" <<
                RESET <<
                ":" <<
                endl <<
                "  check <path>         Run lint and report violations" <<
                endl <<
                "  init <linter> <dir>  Write linter configuration" <<
                endl <<
                "  print <file>         Print AST of a source file" <<
                endl <<
                endl <<
                BOLD <<
                MAGENTA <<
                "Arguments" <<
                RESET <<
                ":" <<
                endl <<
                "  file          Supports '.c', '.cpp', '.java', '.kt', '.kts'" <<
                endl <<
                "  linter        One of 'checkstyle', 'cppcheck', 'codenarc', 'eslint'," <<
                endl <<
                "                'ktlint', 'pylint' or 'typescript-eslint'" <<
                endl <<
                "  path (=self)  Directory or regular file" <<
                endl <<
                "  dir (=self)   Target project directory" <<
                endl <<
                endl;
        cout << options;
        return 0;
    }

    if (!variables.contains("command")) {
        return die("See --help.");
    }

    const string command = variables["command"].as<string>();
    vector<string> subargs = collect_unrecognized(parsed.options, include_positional);
    erase(subargs, command);

    if (command == "check") {
        options_description check_options;
        check_options.add_options()
                ("path", value<string>()->required());

        positional_options_description check_positional;
        check_positional.add("path", 1);

        variables_map check_variables;
        try {
            store(
                command_line_parser(subargs)
                .options(check_options)
                .positional(check_positional)
                .run(),
                check_variables
            );
            notify(check_variables);
        } catch (error &e) {
            return die(e.what());
        }

        return check_source(
            path(check_variables["path"].as<string>()),
            variables["verbose"].as<bool>()
        );
    }

    if (command == "init") {
        options_description init_options;
        init_options.add_options()
                ("linter", value<string>()->required())
                ("dir", value<string>());

        positional_options_description init_positional;
        init_positional.add("linter", 1);
        init_positional.add("dir", 1);

        variables_map init_variables;
        try {
            store(
                command_line_parser(subargs)
                .options(init_options)
                .positional(init_positional)
                .run(),
                init_variables
            );
            notify(init_variables);
        } catch (error &e) {
            return die(e.what());
        }

        const string linter = init_variables["linter"].as<string>();
        string linter_lower = linter;
        if (ranges::transform(linter_lower, linter_lower.begin(), ::tolower);
            ranges::find(AVAILABLE_LINTERS, linter_lower) == AVAILABLE_LINTERS.end()) {
            return die("Unknown linter '" + linter + "'.");
        }
        const path dir =
                init_variables.contains("dir")
                    ? path(init_variables["dir"].as<string>())
                    : current_path();
        if (!is_directory(dir)) {
            return die("Not a directory.");
        }
        return init_config(
            linter,
            dir,
            variables["google"].as<bool>(),
            variables["verbose"].as<bool>()
        );
    }

    if (command == "print") {
        options_description print_options;
        print_options.add_options()
                ("file", value<string>()->required());

        positional_options_description print_positional;
        print_positional.add("file", 1);

        variables_map print_variables;
        try {
            store(
                command_line_parser(subargs)
                .options(print_options)
                .positional(print_positional)
                .run(),
                print_variables
            );
            notify(print_variables);
        } catch (error &e) {
            return die(e.what());
        }

        const auto file = path(print_variables["file"].as<string>());
        if (!is_regular_file(file)) {
            return die("Not a file.");
        }
        if (const auto extension = file.extension().string();
            ranges::find(SUPPORTED_EXTENSIONS, extension) == SUPPORTED_EXTENSIONS.end()) {
            return die("Unsupported extension '" + extension + "'.");
        }
        return print_file(
            file,
            variables["verbose"].as<bool>()
        );
    }

    return die("Unknown command '" + command + "'.");
}
