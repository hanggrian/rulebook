cmake_minimum_required(VERSION 4.1)
project(rulebook-cli)

set(CMAKE_CXX_STANDARD 26)
set(CMAKE_BUILD_TYPE Release)

include(FetchContent)
FetchContent_Declare(
        cmrc
        GIT_REPOSITORY https://github.com/vector-of-bool/cmrc.git
)
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

find_package(
        Boost
        REQUIRED
        COMPONENTS
        program_options
)
add_executable(
        rulebook
        src/cli.cpp
        src/cli.h
        src/colors.h
        src/config_writer.cpp
        src/config_writer.h
        src/main.cpp
)
target_link_libraries(
        rulebook
        PRIVATE
        resources
        Boost::program_options
)
