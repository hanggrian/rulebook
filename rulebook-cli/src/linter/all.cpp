#include <algorithm>
#include <functional>
#include <list>
#include <numeric>
#include <string>
#include "all.h"

using namespace cmrc;
using namespace std;

optional<reference_wrapper<const Linter>> get_linter_by_name(const string &name) {
    string lower_name = name;
    ranges::transform(lower_name, lower_name.begin(), ::tolower);
    const auto linter =
            ranges::find_if(ALL_LINTERS, [&](const Linter &l) {
                return l.name == lower_name;
            });
    if (linter == ALL_LINTERS.end()) {
        return nullopt;
    }
    return *linter;
}

optional<reference_wrapper<const Linter>> get_linter_by_extension(const string &extension) {
    string lower_extension = extension;
    ranges::transform(lower_extension, lower_extension.begin(), ::tolower);
    for (auto linter_ref: ALL_LINTERS) {
        for (const Linter &linter = linter_ref.get();
            const string &expected_extension: linter.file_extensions) {
            if (expected_extension == lower_extension) {
                return linter;
            }
        }
    }
    return nullopt;
}
