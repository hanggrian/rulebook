#include <fstream>
#include <iostream>
#include "cli.h"
#include "file_printer.h"
#include "linter/all.h"

using namespace std;
using namespace std::filesystem;

int print_file(const string &file, const bool verbose) {
    const auto &target_file = path(file);
    if (!is_regular_file(target_file)) {
        return die("Not a file.");
    }
    const string &extension = target_file.extension().string();
    const optional<reference_wrapper<const Linter> > &optional_linter =
        get_linter_by_extension(extension);
    if (!optional_linter.has_value()) {
        return die("Unsupported extension '" + extension + "'.");
    }
    const Linter &actual_linter = *optional_linter;

    if (verbose) {
        cout << "Linter: " << BOLD << actual_linter.name << RESET << endl;
        cout << "Target: " << BOLD << target_file.string() << RESET << endl;
    }

    return actual_linter.print(target_file);
}
