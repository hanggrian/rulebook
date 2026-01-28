cmake_minimum_required(VERSION 4.0)
project(rulebook-cli)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_BUILD_TYPE Release)

find_package(Boost REQUIRED COMPONENTS program_options)

add_executable(
        rulebook
        src/color.h
        src/interactive_config.cpp
        src/interactive_config.h
        src/main.cpp
)

target_link_libraries(
        rulebook
        PRIVATE
        Boost::program_options
)
