cmake_minimum_required(VERSION 4.0)
project(sample)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_BUILD_TYPE Release)

add_executable(
        sample
        c/main.c
        c/abbreviation_as_word.c
        c/assignment_wrap.c
        c/braces_spaces.c
        c/block_comment_spaces.c
        c/block_comment_trim.c
        c/block_tag_indentation.c
        c/block_tag_punctuation.c
        c/case_separator.c
        c/chain_call_wrap.c
        c/class_name.c
        c/comment_spaces.c
        c/comment_trim.c
        c/complicated_assignment.c
        c/duplicate_blank_line.c
        c/duplicate_blank_line_in_block_comment.c
        c/duplicate_blank_line_in_comment.c
        c/duplicate_space.c
        c/file_name.c
        c/file_size.c
        c/final_newline.c
        c/identifier_name.c
        c/illegal_variable_name.c
        c/import_order.c
        c/indent_style.c
        c/line_length.c
        c/lonely_case.c
        c/lonely_if.c
        c/lowercase_f.c
        c/lowercase_hexadecimal.c
        c/meaningless_word.c
        c/operator_wrap.c
        c/parameter_wrap.c
        c/redundant_default.c
        c/redundant_else.c
        c/redundant_if.c
        c/todo_comment.c
        c/unnecessary_return.c
        c/unnecessary_trailing_whitespace.c
        c/uppercase_l.c
        cpp/generic_name.cpp
        cpp/illegal_catch.cpp
        cpp/illegal_throw.cpp
        cpp/inner_class_position.cpp
        cpp/member_order.cpp
        cpp/member_separator.cpp
        cpp/package_name.cpp
        cpp/parentheses_clip.cpp
        cpp/parentheses_trim.cpp
)
