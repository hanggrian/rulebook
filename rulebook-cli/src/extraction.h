#ifndef EXTRACTION_H
#define EXTRACTION_H

#include <filesystem>
#include <string>

std::filesystem::path extract_binary(const std::string &filename);

std::filesystem::path extract_resource(const std::string &filename);

#endif
