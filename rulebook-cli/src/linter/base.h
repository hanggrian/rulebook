#ifndef BASE_H
#define BASE_H

#include <cmrc/cmrc.hpp>
#include <filesystem>
#include <fstream>
#include <list>
#include <optional>

CMRC_DECLARE(resources);

class Linter {
public:
    std::string name;
    std::string default_config;
    std::optional<std::string> google_config;
    std::string config_path;
    std::list<std::string> file_extensions;

    virtual ~Linter() = default;

    [[nodiscard]] virtual int print(const std::filesystem::path &target) const {
        throw std::runtime_error(name + " cannot print AST.");
    }

    [[nodiscard]] virtual int lint(
        const std::filesystem::path &target,
        const bool google_variant
    ) const {
        throw std::runtime_error(name + " cannot run lint.");
    }

    void init(const std::filesystem::path &target, const bool google_variant) const {
        const cmrc::file source_file =
                cmrc::resources::get_filesystem().open("resources/" + get_config(google_variant));
        create_directories(target.parent_path());
        std::ofstream stream(target, std::ios::binary);
        stream.write(source_file.begin(), static_cast<std::streamsize>(source_file.size()));
        stream.close();
    }

    [[nodiscard]] std::string get_config(const bool google_variant) const {
        if (!google_variant) {
            return default_config;
        }
        if (!google_config.has_value()) {
            throw std::invalid_argument("Google variant is unavailable for this linter.");
        }
        return google_config.value();
    }

    bool operator<(const Linter &other) const {
        return name < other.name;
    }
};

#endif
