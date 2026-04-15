#ifndef FILE_PRINTER_H
#define FILE_PRINTER_H

#include <filesystem>

int print_file(const std::filesystem::path &target_file, bool verbose);

#endif
