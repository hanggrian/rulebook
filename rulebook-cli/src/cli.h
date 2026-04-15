#ifndef CLI_H
#define CLI_H

#include <iostream>
#include <string>

inline constexpr std::string RESET = "\033[0m";
inline constexpr std::string BOLD = "\033[1m";

inline constexpr std::string RED = "\033[91m";
inline constexpr std::string YELLOW = "\033[93m";
inline constexpr std::string GREEN = "\033[92m";

inline constexpr std::string CYAN = "\033[96m";
inline constexpr std::string MAGENTA = "\033[95m";
inline constexpr std::string BLUE = "\033[94m";

inline int die(const std::string &msg) {
    std::cerr << RED << msg << RESET << std::endl;
    return 1;
}

inline void warn(const std::string &msg) {
    std::cerr << YELLOW << msg << RESET << std::endl;
}

inline bool is_installed(const std::string &program) {
    return system(("which " + program + " > /dev/null 2>&1").c_str()) == 0;
}

#endif
