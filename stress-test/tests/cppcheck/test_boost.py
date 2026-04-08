from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers import \
    AssignmentWrapChecker, \
    BlockCommentSpacesChecker, \
    ChainCallWrapChecker, \
    DuplicateBlankLineChecker, \
    DuplicateSpaceChecker, \
    GenericNameChecker, \
    ImportOrderChecker, \
    IndentStyleChecker, \
    LineLengthChecker, \
    OperatorWrapChecker, \
    ParameterWrapChecker, \
    ParenthesesClipChecker, \
    ParenthesesTrimChecker, \
    RedundantElseChecker, \
    TodoCommentChecker
from .all_checkers_case import AllCheckersTestCase
from ..code import get_code


class TestBoost(AllCheckersTestCase):
    @patch.object(AssignmentWrapChecker, 'report_error')
    @patch.object(BlockCommentSpacesChecker, 'report_error')
    @patch.object(ChainCallWrapChecker, 'report_error')
    @patch.object(DuplicateBlankLineChecker, 'report_error')
    @patch.object(DuplicateSpaceChecker, 'report_error')
    @patch.object(GenericNameChecker, 'report_error')
    @patch.object(ImportOrderChecker, 'report_error')
    @patch.object(IndentStyleChecker, 'report_error')
    @patch.object(LineLengthChecker, 'report_error')
    @patch.object(OperatorWrapChecker, 'report_error')
    @patch.object(ParameterWrapChecker, 'report_error')
    @patch.object(ParenthesesClipChecker, 'report_error')
    @patch.object(ParenthesesTrimChecker, 'report_error')
    @patch.object(RedundantElseChecker, 'report_error')
    @patch.object(TodoCommentChecker, 'report_error')
    def test_boost_options_description(
        self,
        todo_comment_report_error,
        redundant_else_report_error,
        parentheses_trim_report_error,
        parentheses_clip_report_error,
        parameter_wrap_report_error,
        operator_wrap_report_error,
        line_length_report_error,
        indent_style_report_error,
        import_order_report_error,
        generic_name_report_error,
        duplicate_whitespace_report_error,
        duplicate_blank_line_report_error,
        chain_call_wrap_report_error,
        block_comment_spaces_report_error,
        assignment_wrap_report_error,
    ):
        config = self.dump_configs(get_code('cppcheck', 'boost', 'options_description.cpp'))[0]
        [checker.run_check(config) for checker in self.checkers]
        tokens = config.tokenlist
        assignment_wrap_report_error.assert_has_calls(
            [self.assignment_wrap_called(tokens, 353)],
        )
        block_comment_spaces_report_error.assert_has_calls(
            [self.block_comment_spaces_called(tokens, 700, 2, 657)],
        )
        chain_call_wrap_report_error.assert_has_calls(
            [
                self.chain_call_wrap_called(tokens, 241, 39),
                self.chain_call_wrap_called(tokens, 241, 55),
                self.chain_call_wrap_called(tokens, 242, 48),
            ],
            any_order=True,
        )
        duplicate_blank_line_report_error.assert_has_calls(
            [
                self.duplicate_blank_line_called(tokens, '}', 700, 2, 16),
                self.duplicate_blank_line_called(tokens, '}', 700, 2, 47),
                self.duplicate_blank_line_called(tokens, '}', 700, 2, 60),
                self.duplicate_blank_line_called(tokens, '}', 700, 2, 89),
                self.duplicate_blank_line_called(tokens, '}', 700, 2, 174),
            ],
            any_order=True,
        )
        duplicate_whitespace_report_error.assert_has_calls(
            [
                self.duplicate_whitespace_called(tokens, '}', 700, 2, 35, 34),
                self.duplicate_whitespace_called(tokens, '}', 700, 2, 45, 6),
                self.duplicate_whitespace_called(tokens, '}', 700, 2, 484, 39),
                self.duplicate_whitespace_called(tokens, '}', 700, 2, 489, 45),
                self.duplicate_whitespace_called(tokens, '}', 700, 2, 501, 45),
                self.duplicate_whitespace_called(tokens, '}', 700, 2, 589, 32),
                self.duplicate_whitespace_called(tokens, '}', 700, 2, 592, 40),
                self.duplicate_whitespace_called(tokens, '}', 700, 2, 609, 14),
            ],
            any_order=True,
        )
        generic_name_report_error.assert_has_calls(
            [self.generic_name_called(tokens, 'charT', 34)],
        )
        import_order_report_error.assert_has_calls(
            [
                self.import_order_called(
                    tokens,
                    '}',
                    700,
                    2,
                    "Remove blank line before directive 'boost/program_options/parsers.hpp'.",
                    14,
                ),
                self.import_order_called(
                    tokens,
                    '}',
                    700,
                    2,
                    "Arrange directive 'boost/lexical_cast.hpp' before " +
                    "'boost/program_options/parsers.hpp'.",
                    17,
                ),
                self.import_order_called(
                    tokens,
                    '}',
                    700,
                    2,
                    "Remove blank line before directive 'boost/lexical_cast.hpp'.",
                    17,
                ),
                self.import_order_called(
                    tokens,
                    '}',
                    700,
                    2,
                    "Arrange directive 'boost/detail/workaround.hpp' before 'boost/tokenizer.hpp'.",
                    19,
                ),
                self.import_order_called(
                    tokens,
                    '}',
                    700,
                    2,
                    "Remove blank line before directive 'cassert'.",
                    22,
                ),
                self.import_order_called(
                    tokens,
                    '}',
                    700,
                    2,
                    "Arrange directive 'cstdarg' before 'cstring'.",
                    25,
                ),
                self.import_order_called(
                    tokens,
                    '}',
                    700,
                    2,
                    "Arrange directive 'iterator' before 'sstream'.",
                    27,
                ),
            ],
            any_order=True,
        )
        indent_style_report_error.assert_has_calls(
            [
                self.indent_style_called(tokens, 'namespace', 32),
                self.indent_style_called(tokens, 'template', 34),
                self.indent_style_called(tokens, 'std', 35),
                self.indent_style_called(tokens, '{', 36),
                self.indent_style_called(tokens, 'std', 37),
                self.indent_style_called(tokens, 'for', 38),
                self.indent_style_called(tokens, '{', 39),
                self.indent_style_called(tokens, 'result', 40),
                self.indent_style_called(tokens, '}', 41),
                self.indent_style_called(tokens, 'return', 42),
                self.indent_style_called(tokens, '}', 43),
                self.indent_style_called(tokens, 'const', 54),
                self.indent_style_called(tokens, 'const', 63),
                self.indent_style_called(tokens, 'const', 64),
                self.indent_style_called(tokens, 'bool', 76),
                self.indent_style_called(tokens, 'bool', 77),
                self.indent_style_called(tokens, 'bool', 78),
                self.indent_style_called(tokens, 'append', 242),
                self.indent_style_called(tokens, 'const', 264),
                self.indent_style_called(tokens, 'const', 279),
                self.indent_style_called(tokens, 'const', 289),
                self.indent_style_called(tokens, 'const', 290),
                self.indent_style_called(tokens, 'int', 301),
                self.indent_style_called(tokens, 'int', 310),
                self.indent_style_called(tokens, 'int', 311),
                self.indent_style_called(tokens, 'bool', 349),
                self.indent_style_called(tokens, 'bool', 350),
                self.indent_style_called(tokens, 'bool', 351),
                self.indent_style_called(tokens, 'long_ignore_case', 354),
                self.indent_style_called(tokens, 'bool', 368),
                self.indent_style_called(tokens, 'bool', 369),
                self.indent_style_called(tokens, 'bool', 370),
                self.indent_style_called(tokens, 'std', 436),
                self.indent_style_called(tokens, 'int', 437),
                self.indent_style_called(tokens, 'int', 438),
                self.indent_style_called(tokens, '(', 499),
                self.indent_style_called(tokens, 'reverse_iterator', 520),
                self.indent_style_called(tokens, "' '", 521),
                self.indent_style_called(tokens, 'line_length', 595),
                self.indent_style_called(tokens, 'os', 625),
                self.indent_style_called(tokens, 'for', 626),
                self.indent_style_called(tokens, '{', 627),
                self.indent_style_called(tokens, 'os', 628),
                self.indent_style_called(tokens, '}', 629),
                self.indent_style_called(tokens, 'for', 631),
                self.indent_style_called(tokens, '{', 632),
                self.indent_style_called(tokens, 'os', 633),
                self.indent_style_called(tokens, '}', 634),
                self.indent_style_called(tokens, 'first_column_width', 638),
            ],
            any_order=True,
        )
        line_length_report_error.assert_has_calls(
            [
                self.line_length_called(tokens, 83),
                self.line_length_called(tokens, 542),
                self.line_length_called(tokens, 631),
            ],
            any_order=True,
        )
        operator_wrap_report_error.assert_has_calls(
            [self.operator_wrap_called(tokens, '==', 95, 25, False)],
        )
        parameter_wrap_report_error.assert_has_calls(
            [
                self.parameter_wrap_called(tokens, 'approx', 353, 58),
                self.parameter_wrap_called(tokens, 'short_ignore_case', 354, 58),
                self.parameter_wrap_called(tokens, '*', 594, 38),
                self.parameter_wrap_called(tokens, 'first_column_width', 594, 49),
                self.parameter_wrap_called(tokens, 'const', 612, 43),
                self.parameter_wrap_called(tokens, 'int', 613, 54),
                self.parameter_wrap_called(tokens, 'opt', 637, 40),
                self.parameter_wrap_called(tokens, 'line_length', 638, 56),
            ],
            any_order=True,
        )
        parentheses_clip_report_error.assert_has_calls(
            [
                self.parentheses_clip_called(tokens, 49, 5),
                self.parentheses_clip_called(tokens, 71, 5),
            ],
            any_order=True,
        )
        parentheses_trim_report_error.assert_has_calls(
            [
                self.parentheses_trim_called(tokens, '{', 30, 45, 31),
                self.parentheses_trim_called(tokens, '{', 32, 14, 33),
                self.parentheses_trim_called(tokens, '}', 45, 5, 44),
                self.parentheses_trim_called(tokens, '{', 87, 43, 88),
                self.parentheses_trim_called(tokens, '}', 113, 9, 112),
                self.parentheses_trim_called(tokens, '}', 700, 1, 699),
            ],
            any_order=True,
        )
        redundant_else_report_error.assert_has_calls(
            [
                self.redundant_else_called(tokens, 104),
                self.redundant_else_called(tokens, 142),
                self.redundant_else_called(tokens, 170),
                self.redundant_else_called(tokens, 252),
            ],
            any_order=True,
        )
        todo_comment_report_error.assert_has_calls(
            [
                self.todo_comment_called(
                    tokens,
                    '}',
                    700,
                    2,
                    "Omit separator ':'.",
                    12,
                ),
                self.todo_comment_called(
                    tokens,
                    '}',
                    700,
                    2,
                    "Omit separator ':'.",
                    268,
                ),
                self.todo_comment_called(
                    tokens,
                    '}',
                    700,
                    2,
                    "Omit separator ':'.",
                    396,
                ),
            ],
            any_order=True,
        )


if __name__ == '__main__':
    main()
