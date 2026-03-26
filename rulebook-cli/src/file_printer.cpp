#include <fstream>
#include <iostream>
#include "cli.hpp"
#include "file_printer.hpp"
#include "linters.hpp"

using namespace std;

int print_file(const path &target_file, const bool verbose) {
    const Linter &linter = get_linter_by_extension(target_file.extension());

    if (verbose) {
        cout << "Linter: " << BOLD << linter.name << RESET << endl;
        cout << "Target: " << BOLD << target_file.string() << RESET << endl;
    }

    return linter.print(target_file);
}
