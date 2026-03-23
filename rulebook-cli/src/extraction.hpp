#ifndef BINARY_H
#define BINARY_H

#include <filesystem>
#include <string>

using namespace std;
using namespace std::filesystem;

path extract_binary(const string &filename);

path extract_resource(const string &filename);

#endif
