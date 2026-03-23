cmake_minimum_required(VERSION 4.1)
project(rulebook-cli)

set(CMAKE_CXX_STANDARD 26)
set(CMAKE_BUILD_TYPE Release)

set(VERSION "0.2")
set(KTLINT_VERSION "1.8.0")
set(KTLINT_VERSION2 "0.47.0") # the last version with printAST command
set(KOTLIN_VERSION "2.3.20")
set(CHECKSTYLE_VERSION "11.1.0")

include(FetchContent)
FetchContent_Declare(cmrc GIT_REPOSITORY https://github.com/vector-of-bool/cmrc.git)
FetchContent_MakeAvailable(cmrc)
cmrc_add_resource_library(
        resources
        resources/checkstyle_google.xml
        resources/checkstyle_sun.xml
        resources/codenarc.xml
        resources/cppcheck_core.json
        resources/cppcheck_google.json
        resources/eslint_crockford.config.js
        resources/eslint_google.config.js
        resources/ktlint.editorconfig
        resources/pylint_google
        resources/pylint_pylint
        resources/typescript_eslint_crockford.config.js
        resources/typescript_eslint_google.config.js
)

file(
        DOWNLOAD
        "https://github.com/pinterest/ktlint/releases/download/${KTLINT_VERSION}/ktlint"
        "${CMAKE_BINARY_DIR}/ktlint"
)
file(
        DOWNLOAD
        "https://github.com/pinterest/ktlint/releases/download/${KTLINT_VERSION2}/ktlint"
        "${CMAKE_BINARY_DIR}/ktlint2"
)
file(
        DOWNLOAD
        "https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/${KOTLIN_VERSION}/kotlin-stdlib-${KOTLIN_VERSION}.jar"
        "${CMAKE_BINARY_DIR}/kotlin-stdlib.jar"
)
file(
        DOWNLOAD
        "https://github.com/checkstyle/checkstyle/releases/download/checkstyle-${CHECKSTYLE_VERSION}/checkstyle-${CHECKSTYLE_VERSION}-all.jar"
        "${CMAKE_BINARY_DIR}/checkstyle.jar"
)
configure_file(
        "$ENV{HOME}/GitHub/rulebook/rulebook-checkstyle/build/libs/rulebook-checkstyle-${VERSION}.jar"
        "${CMAKE_BINARY_DIR}/rulebook-checkstyle.jar"
        COPYONLY
)
configure_file(
        "$ENV{HOME}/GitHub/rulebook/rulebook-ktlint/build/libs/rulebook-ktlint-${VERSION}.jar"
        "${CMAKE_BINARY_DIR}/rulebook-ktlint.jar"
        COPYONLY
)
add_custom_command(
        OUTPUT "${CMAKE_BINARY_DIR}/ktlint_bin.h"
        COMMAND xxd -i ktlint > "${CMAKE_BINARY_DIR}/ktlint_bin.h"
        WORKING_DIRECTORY "${CMAKE_BINARY_DIR}"
        DEPENDS "${CMAKE_BINARY_DIR}/ktlint"
)
add_custom_command(
        OUTPUT "${CMAKE_BINARY_DIR}/ktlint_bin2.h"
        COMMAND xxd -i ktlint2 > "${CMAKE_BINARY_DIR}/ktlint_bin2.h"
        WORKING_DIRECTORY "${CMAKE_BINARY_DIR}"
        DEPENDS "${CMAKE_BINARY_DIR}/ktlint2"
)
add_custom_command(
        OUTPUT "${CMAKE_BINARY_DIR}/kotlin_stdlib_bin.h"
        COMMAND xxd -i -n kotlin_stdlib_jar "kotlin-stdlib.jar" > "${CMAKE_BINARY_DIR}/kotlin_stdlib_bin.h"
        WORKING_DIRECTORY "${CMAKE_BINARY_DIR}"
        DEPENDS "${CMAKE_BINARY_DIR}/kotlin-stdlib.jar"
)
add_custom_command(
        OUTPUT "${CMAKE_BINARY_DIR}/checkstyle_bin.h"
        COMMAND xxd -i -n checkstyle_jar "checkstyle.jar" > "${CMAKE_BINARY_DIR}/checkstyle_bin.h"
        WORKING_DIRECTORY "${CMAKE_BINARY_DIR}"
        DEPENDS "${CMAKE_BINARY_DIR}/checkstyle.jar"
)
add_custom_command(
        OUTPUT "${CMAKE_BINARY_DIR}/rulebook_checkstyle_bin.h"
        COMMAND xxd -i -n rulebook_checkstyle_jar "rulebook-checkstyle.jar" > "${CMAKE_BINARY_DIR}/rulebook_checkstyle_bin.h"
        WORKING_DIRECTORY "${CMAKE_BINARY_DIR}"
        DEPENDS "${CMAKE_BINARY_DIR}/rulebook-checkstyle.jar"
)
add_custom_command(
        OUTPUT "${CMAKE_BINARY_DIR}/rulebook_ktlint_bin.h"
        COMMAND xxd -i -n rulebook_ktlint_jar "rulebook-ktlint.jar" > "${CMAKE_BINARY_DIR}/rulebook_ktlint_bin.h"
        WORKING_DIRECTORY "${CMAKE_BINARY_DIR}"
        DEPENDS "${CMAKE_BINARY_DIR}/rulebook-ktlint.jar"
)
add_custom_target(ktlint_embed DEPENDS "${CMAKE_BINARY_DIR}/ktlint_bin.h")
add_custom_target(ktlint_embed2 DEPENDS "${CMAKE_BINARY_DIR}/ktlint_bin2.h")
add_custom_target(checkstyle_embed DEPENDS "${CMAKE_BINARY_DIR}/checkstyle_bin.h")
add_custom_target(kotlin_stdlib_embed DEPENDS "${CMAKE_BINARY_DIR}/kotlin_stdlib_bin.h")
add_custom_target(rulebook_checkstyle_embed DEPENDS "${CMAKE_BINARY_DIR}/rulebook_checkstyle_bin.h")
add_custom_target(rulebook_ktlint_embed DEPENDS "${CMAKE_BINARY_DIR}/rulebook_ktlint_bin.h")

find_package(Boost REQUIRED COMPONENTS program_options)
add_executable(
        rulebook
        src/main.cpp
        src/cli.cpp
        src/cli.hpp
        src/colors.hpp
        src/config_initializer.cpp
        src/config_initializer.hpp
        src/extraction.cpp
        src/extraction.hpp
        src/file_printer.cpp
        src/file_printer.hpp
        src/source_checker.cpp
        src/source_checker.hpp
)
add_dependencies(
        rulebook
        ktlint_embed
        ktlint_embed2
        checkstyle_embed
        kotlin_stdlib_embed
        rulebook_checkstyle_embed
        rulebook_ktlint_embed
)
target_include_directories(
        rulebook
        PRIVATE
        "${CMAKE_BINARY_DIR}"
)
target_link_libraries(
        rulebook
        PRIVATE
        resources
        Boost::program_options
)
target_compile_definitions(
        rulebook
        PRIVATE
        VERSION="${VERSION}"
)
