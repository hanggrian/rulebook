cmake_minimum_required(VERSION 4.0)
project(sample)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_BUILD_TYPE Release)

add_executable(
        sample
        c/abbreviation_as_word.c
        c/assignment_wrapping.c
        c/block_comment_spacing.c
        c/block_comment_trimming.c
        c/block_tag_indentation.c
        c/block_tag_punctuation.c
        c/case_separator.c
        c/chain_call_wrapping.c
        c/class_name.c
        c/comment_spacing.c
        c/comment_trimming.c
        c/duplicate_blank_line.c
        c/duplicate_blank_line_in_block_comment.c
        c/duplicate_blank_line_in_comment.c
        c/duplicate_space.c
        c/file_size.c
        c/final_newline.c
        c/identifier_name.c
        c/illegal_variable_name.c
        c/import_order.c
        c/indent_style.c
        c/line_length.c
        c/lonely_case.c
        c/lowercase_f.c
        c/lowercase_hexadecimal.c
        c/meaningless_word.c
        c/operator_wrapping.c
        c/parameter_wrapping.c
        c/redundant_default.c
        c/redundant_else.c
        c/todo_comment.c
        c/uppercase_l.c
        cpp/generic_name.cpp
        cpp/illegal_catch.cpp
        cpp/illegal_throw.cpp
        cpp/inner_class_position.cpp
        cpp/member_order.cpp
        cpp/member_separator.cpp
        cpp/package_name.cpp
        cpp/parentheses_clipping.cpp
        cpp/parentheses_trimming.cpp
)
