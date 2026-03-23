#include <fstream>
#include <iostream>
#include "extraction.hpp"
#include "cli.hpp"
#include "colors.hpp"
#include "file_printer.hpp"

using namespace std;

int print_file(const path &file, bool verbose) {
    const string extension = file.extension().string();
    string binary;
    if (extension == ".c" || extension == ".cpp") {
        binary = "cppcheck";
    } else if (extension == ".java") {
        binary = extract_binary("checkstyle.jar").string();
    } else {
        binary = extract_binary("ktlint2").string();
    }

    if (verbose) {
        cout << "File:   " << BOLD << file.string() << RESET << endl;
        cout << "Binary: " << BOLD << binary << RESET << endl << endl;
    }

    string command;
    if (binary == "cppcheck") {
        if (is_installed(binary)) {
            return die("Missing Cppcheck.");
        }
        const auto dump_file = path(file.string() + ".dump");
        system((binary + "--dump " + file.string()).c_str());
        ifstream dump(dump_file);
        cout << dump.rdbuf();
        remove(dump_file);
    } else if (binary.ends_with("checkstyle.jar")) {
        if (is_installed("java")) {
            return die("Missing JRE or JDK.");
        }
        command = "java -jar " + binary + " -T " + file.string();
    } else {
        command = binary + " --color printAST " + file.string();
    }
    return system(command.c_str());
}
