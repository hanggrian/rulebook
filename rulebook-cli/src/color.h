#ifndef COLOR_H
#define COLOR_H

#include <string>

using namespace std;

namespace colors {
    inline const string &reset() {
        static const string value = "\033[0m";
        return value;
    }

    inline const string &bold() {
        static const string value = "\033[1m";
        return value;
    }

    inline const string &red() {
        static const string value = "\033[31m";
        return value;
    }

    inline const string &green() {
        static const string value = "\033[32m";
        return value;
    }

    inline const string &yellow() {
        static const string value = "\033[33m";
        return value;
    }

    inline const string &cyan() {
        static const string value = "\033[36m";
        return value;
    }
}

#endif
