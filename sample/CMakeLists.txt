cmake_minimum_required(VERSION 4.0)
project(sample)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_BUILD_TYPE Release)

add_executable(
        sample
        c/main.c
        c/abbreviation_as_word.c
        c/assignment_wrapping.c
        c/block_comment_spacing.c
        c/block_comment_trimming.c
        c/block_tag_indentation.c
        c/block_tag_punctuation.c
        c/braces_spacing.c
        c/case_separator.c
        c/chain_call_wrapping.c
        c/class_name.c
        c/comment_spacing.c
        c/comment_trimming.c
        c/file_name.c
        c/identifier_name.c
        c/illegal_variable_name.c
        c/import_order.c
        c/indent_style.c
        c/lowercase_f.c
        c/lowercase_hex.c
        c/max_file_size.c
        c/max_line_length.c
        c/no_duplicate_blank_line.c
        c/no_duplicate_blank_line_in_block_comment.c
        c/no_duplicate_blank_line_in_comment.c
        c/no_duplicate_space.c
        c/no_lonely_case.c
        c/no_lonely_if.c
        c/no_meaningless_word.c
        c/no_redundant_default.c
        c/no_redundant_else.c
        c/no_redundant_if.c
        c/no_trailing_space.c
        c/no_useless_return.c
        c/operator_wrapping.c
        c/parameter_wrapping.c
        c/shorthand_assignment.c
        c/todo_comment_style.c
        c/trailing_newline.c
        c/uppercase_l.c
        cpp/generic_name.cpp
        cpp/inner_class_position.cpp
        cpp/member_order.cpp
        cpp/member_separator.cpp
        cpp/no_broad_catch.cpp
        cpp/no_broad_throw.cpp
        cpp/package_name.cpp
        cpp/parentheses_clipping.cpp
        cpp/parentheses_trimming.cpp
)
