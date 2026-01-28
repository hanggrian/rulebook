cmake_minimum_required(VERSION 4.0)
project(sample)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_BUILD_TYPE Release)

add_executable(
        sample
        c/file_size.c
        c/final_newline.c
        c/indent_style.c
        c/line_length.c
        c/unnecessary_switch.c
)
