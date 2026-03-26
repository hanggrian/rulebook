#include <algorithm>
#include <iostream>
#include <ranges>
#include <set>
#include <string>
#include "cli.hpp"
#include "linters.hpp"
#include "source_linter.hpp"

using namespace std;

namespace {
    const Linter &get_linter(const path &path) {
        return get_linter_by_extension(path.extension().string());
    }
}

int run_lint(const path &target_path, const bool google_variant, const bool verbose) {
    set<reference_wrapper<const Linter> > linters = {};
    if (is_regular_file(target_path)) {
        linters.insert(get_linter(target_path));
    } else {
        for (const auto &entry: recursive_directory_iterator(target_path)) {
            linters.insert(get_linter(entry.path()));
        }
    }

    if (verbose) {
        auto names = linters | views::transform([](const Linter &l) { return l.name; });
        cout <<
                "Linters: " <<
                BOLD <<
                ranges::fold_left(names, string{}, [](const string &a, const string &b) {
                    return a.empty() ? b : a + ", " + b;
                }) <<
                RESET <<
                endl;
        cout << "Path:    " << BOLD << target_path.string() << RESET << endl << endl;
    }

    return ranges::all_of(linters, [&](const auto &linter) {
        return linter.get().lint(target_path, google_variant);
    });
}
