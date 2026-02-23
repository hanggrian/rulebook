cmake_minimum_required(VERSION 4.0)
project(sample)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_BUILD_TYPE Release)

add_executable(
        sample
        c/assignment_wrap.c
        c/block_comment_space.c
        c/block_comment_trim.c
        c/block_tag_indentation.c
        c/block_tag_punctuation.c
        c/case_separator.c
        c/chain_call_wrap.c
        c/class_name.c
        c/class_name_abbreviation.c
        c/comment_space.c
        c/comment_trim.c
        c/duplicate_blank_line.c
        c/duplicate_blank_line_in_block_comment.c
        c/duplicate_blank_line_in_comment.c
        c/duplicate_space.c
        c/file_size.c
        c/final_newline.c
        c/identifier_name.c
        c/illegal_class_name_suffix.c
        c/illegal_variable_name.c
        c/import_order.c
        c/indent_style.c
        c/line_length.c
        c/lowercase_f.c
        c/lowercase_hex.c
        c/operator_wrap.c
        c/parameter_wrap.c
        c/todo_comment.c
        c/unnecessary_switch.c
        c/uppercase_l.c
        cpp/empty_parentheses_clip.cpp
        cpp/illegal_catch.cpp
        cpp/illegal_throw.cpp
        cpp/inner_class_position.cpp
        cpp/member_order.cpp
        cpp/member_separator.cpp
        cpp/package_name.cpp
        cpp/parentheses_trim.cpp
        cpp/required_generics_name.cpp
)
