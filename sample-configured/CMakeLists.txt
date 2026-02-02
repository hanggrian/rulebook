cmake_minimum_required(VERSION 4.0)
project(sample-configured)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_BUILD_TYPE Release)

add_executable(
        sample
        c/block_tag_punctuation.c
        c/file_size.c
        c/illegal_class_name_suffix.c
        c/illegal_variable_name.c
        c/indent_style.c
        c/line_length.c
        cpp/member_order.cpp
        cpp/required_generics_name.cpp
)
