#include <algorithm>
#include <iostream>
#include <ranges>
#include <set>
#include <string>
#include "cli.h"
#include "linter/all.h"
#include "source_linter.h"

using namespace std;
using namespace std::filesystem;

namespace {
    void insert_not_null(set<reference_wrapper<const Linter> > &collection, const path &path) {
        const optional<reference_wrapper<const Linter> > &element =
            get_linter_by_extension(path.extension().string());
        if (element.has_value()) {
            collection.insert(*element);
        }
    }
}

int run_lint(
    const path &target_path,
    const bool google_variant,
    const bool silent
) {
    set<reference_wrapper<const Linter> > linters = {};
    if (is_regular_file(target_path)) {
        insert_not_null(linters, target_path);
    } else {
        for (const auto &entry: recursive_directory_iterator(target_path)) {
            insert_not_null(linters, entry.path());
        }
    }
    if (linters.empty()) {
        return die("No supported source code found in directory.");
    }

    if (!silent) {
        auto names = linters | views::transform([](const Linter &l) { return l.name; });
        cout <<
                "\U0001f4e6 Linters: " <<
                BOLD <<
                ranges::fold_left(names, string{}, [](const string &a, const string &b) {
                    return a.empty() ? b : a + ", " + b;
                }) <<
                RESET <<
                endl;
        cout << "\U0001f4c4 Path:    " << BOLD << target_path.string() << RESET << endl << endl;
    }

    return ranges::all_of(linters, [&](const auto &linter) {
        return linter.get().lint(target_path, google_variant);
    });
}
