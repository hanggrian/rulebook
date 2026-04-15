#include <fstream>
#include <iostream>
#include "cli.h"
#include "file_printer.h"
#include "linters.h"

using namespace std;
using namespace std::filesystem;

int print_file(const path &target_file, const bool verbose) {
    const Linter &linter = get_linter_by_extension(target_file.extension());

    if (verbose) {
        cout << "Linter: " << BOLD << linter.name << RESET << endl;
        cout << "Target: " << BOLD << target_file.string() << RESET << endl;
    }

    return linter.print(target_file);
}
