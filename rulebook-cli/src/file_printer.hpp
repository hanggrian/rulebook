#ifndef FILE_PRINTER_H
#define FILE_PRINTER_H

#include <filesystem>

using namespace std::filesystem;

int print_file(const path &target_file, bool verbose);

#endif
