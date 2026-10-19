cmake_minimum_required(VERSION 4.0)
project(sample-configured)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_BUILD_TYPE Release)

add_executable(
        sample
        c/main.c
        c/block_tag_punctuation.c
        c/illegal_variable_name.c
        c/indent_style.c
        c/max_file_size.c
        c/max_line_length.c
        c/no_meaningless_word.c
        cpp/member_order.cpp
)
