#ifndef LINTERS_H
#define LINTERS_H

#include <cmrc/cmrc.hpp>
#include <filesystem>
#include <fstream>
#include <iostream>
#include <optional>
#include "cli.h"
#include "extraction.h"

CMRC_DECLARE(resources);

class Linter {
public:
    std::string name;
    std::string default_config;
    std::optional<std::string> google_config;
    std::string relative_path;

    virtual ~Linter() = default;

    [[nodiscard]] virtual int print(const std::filesystem::path &target) const {
        throw std::runtime_error(name + " cannot print AST.");
    }

    [[nodiscard]] virtual int lint(const std::filesystem::path &target, const bool google_variant) const {
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
        if (google_variant) {
            if (!google_config.has_value()) {
                throw std::invalid_argument("Google variant is unavailable for this linter.");
            }
            return google_config.value();
        }
        return default_config;
    }

    bool operator<(const Linter &other) const {
        return name < other.name;
    }
};

class CheckstyleLinter : public Linter {
public:
    CheckstyleLinter() {
        name = "checkstyle";
        default_config = "checkstyle_sun.xml";
        google_config = "checkstyle_google.xml";
        relative_path = "config/checkstyle/checkstyle.xml";
    }

    [[nodiscard]] int print(const std::filesystem::path &target) const override {
        check_installed();
        const std::string binary = extract_binary("checkstyle.jar").string();
        const std::string target_value = target.string();
        return system(
            (
                "java -jar " +
                binary +
                " -T " +
                target_value +
                " && java -jar " +
                binary +
                " -J " +
                target_value
            ).c_str()
        );
    }

    [[nodiscard]] int lint(const std::filesystem::path &target, const bool google_variant) const override {
        check_installed();
        return system(
            (
                "java -cp " +
                extract_binary("rulebook-checkstyle.jar").string() +
                ":" +
                extract_binary("kotlin-stdlib.jar").string() +
                ":" +
                extract_binary("checkstyle.jar").string() +
                " com.puppycrawl.tools.checkstyle.Main -c " +
                extract_resource(get_config(google_variant)).string() +
                " " +
                target.string()
            ).c_str()
        );
    }

    static void check_installed() {
        if (!is_installed("java")) {
            throw std::runtime_error("Missing JRE or JDK.");
        }
    }
};

class CodenarcLinter : public Linter {
public:
    CodenarcLinter() {
        name = "codenarc";
        default_config = "codenarc.xml";
        google_config = std::nullopt;
        relative_path = "config/codenarc/codenarc.xml";
    }
};

class CppcheckLinter : public Linter {
public:
    CppcheckLinter() {
        name = "cppcheck";
        default_config = "cppcheck_core.json";
        google_config = "cppcheck_google.json";
        relative_path = "addon.json";
    }

    [[nodiscard]] int print(const std::filesystem::path &target) const override {
        check_installed();
        const auto dump_file = std::filesystem::path(target.string() + ".dump");
        const int result = system(("cppcheck --dump " + target.string()).c_str());
        const std::ifstream dump(dump_file);
        std::cout << dump.rdbuf();
        remove(dump_file);
        return result;
    }

    [[nodiscard]] int lint(const std::filesystem::path &target, const bool google_variant) const override {
        check_installed();
        std::string command = "cppheck ";
        command += "--enable=performance,portability,style,warning ";
        command += "--check-level=exhaustive ";
        command += " --addon=";
        return system(
            (
                command +
                extract_resource(get_config(google_variant)).string() +
                " " +
                target.string()
            ).c_str()
        );
    }

    static void check_installed() {
        if (!is_installed("cppcheck")) {
            throw std::runtime_error("Missing Cppcheck.");
        }
    }
};

class EslintLinter : public Linter {
public:
    EslintLinter() {
        name = "eslint";
        default_config = "eslint_crockford.config.js";
        google_config = "eslint_google.config.js";
        relative_path = "eslint.config.js";
    }
};

class KtlintLinter : public Linter {
public:
    KtlintLinter() {
        name = "ktlint";
        default_config = "ktlint.editorconfig";
        google_config = std::nullopt;
        relative_path = ".editorconfig";
    }

    [[nodiscard]] int print(const std::filesystem::path &target) const override {
        return system(
            (extract_binary("ktlint_print").string() + " --color printAST " + target.string())
            .c_str()
        );
    }

    [[nodiscard]] int lint(const std::filesystem::path &target, const bool _) const override {
        return system(
            (
                extract_binary("ktlint").string() +
                " -R " +
                extract_binary("rulebook-ktlint.jar").string() +
                " " +
                target.string()
            ).c_str()
        );
    }
};

class PylintLinter : public Linter {
public:
    PylintLinter() {
        name = "pylint";
        default_config = "pylint_pylint";
        google_config = "pylint_google";
        relative_path = ".pylintrc";
    }
};

class TypescriptEslintLinter : public Linter {
public:
    TypescriptEslintLinter() {
        name = "typescript-eslint";
        default_config = "typescript_eslint_crockford.config.js";
        google_config = "typescript_eslint_google.config.js";
        relative_path = "eslint.config.js";
    }
};

const CheckstyleLinter CHECKSTYLE;
const CodenarcLinter CODENARC;
const CppcheckLinter CPPCHECK;
const EslintLinter ESLINT;
const KtlintLinter KTLINT;
const PylintLinter PYLINT;
const TypescriptEslintLinter TYPESCRIPTESLINT;

const Linter &get_linter_by_name(const std::string &name);

const Linter &get_linter_by_extension(const std::string &extension);

#endif
