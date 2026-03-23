#ifndef FILE_PRINTER_H
#define FILE_PRINTER_H

#include <filesystem>

using namespace std::filesystem;

int print_file(const path &file, bool verbose);

#endif
