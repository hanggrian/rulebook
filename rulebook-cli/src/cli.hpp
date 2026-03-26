#ifndef CLI_H
#define CLI_H

#include <iostream>
#include <string>

using namespace std;

inline constexpr string RESET = "\033[0m";
inline constexpr string BOLD = "\033[1m";

inline constexpr string RED = "\033[91m";
inline constexpr string YELLOW = "\033[93m";
inline constexpr string GREEN = "\033[92m";

inline constexpr string CYAN = "\033[96m";
inline constexpr string MAGENTA = "\033[95m";
inline constexpr string BLUE = "\033[94m";

inline int die(const string &msg) {
    cerr << RED << msg << RESET << endl;
    return 1;
}

inline void warn(const string &msg) {
    cerr << YELLOW << msg << RESET << endl;
}

inline bool is_installed(const string &program) {
    return system(("which " + program + " > /dev/null 2>&1").c_str()) == 0;
}

#endif
