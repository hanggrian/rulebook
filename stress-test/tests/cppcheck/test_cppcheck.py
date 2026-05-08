from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers import \
    AssignmentWrapChecker, \
    CaseSeparatorChecker, \
    ComplicatedAssignmentChecker, \
    DuplicateBlankLineChecker, \
    DuplicateSpaceChecker, \
    IdentifierNameChecker, \
    IllegalCatchChecker, \
    ImportOrderChecker, \
    IndentStyleChecker, \
    LineLengthChecker, \
    OperatorWrapChecker, \
    ParameterWrapChecker, \
    TodoCommentChecker
from .all_checkers_case import AllCheckersTestCase
from ..code import get_code


class TestCppcheck(AllCheckersTestCase):
    @patch.object(AssignmentWrapChecker, 'report_error')
    @patch.object(CaseSeparatorChecker, 'report_error')
    @patch.object(ComplicatedAssignmentChecker, 'report_error')
    @patch.object(DuplicateBlankLineChecker, 'report_error')
    @patch.object(DuplicateSpaceChecker, 'report_error')
    @patch.object(IdentifierNameChecker, 'report_error')
    @patch.object(IllegalCatchChecker, 'report_error')
    @patch.object(ImportOrderChecker, 'report_error')
    @patch.object(IndentStyleChecker, 'report_error')
    @patch.object(LineLengthChecker, 'report_error')
    @patch.object(OperatorWrapChecker, 'report_error')
    @patch.object(ParameterWrapChecker, 'report_error')
    @patch.object(TodoCommentChecker, 'report_error')
    def test_cppcheck_suppression(
        self,
        todo_comment_report_error,
        parameter_wrap_report_error,
        operator_wrap_report_error,
        line_length_report_error,
        indent_style_report_error,
        import_order_report_error,
        illegal_catch_report_error,
        identifier_name_report_error,
        duplicate_whitespace_report_error,
        duplicate_blank_line_report_error,
        complicated_assignment_report_error,
        case_separator_report_error,
        assignment_wrap_report_error,
    ):
        config = self.dump_configs(get_code('cppcheck', 'cppcheck', 'suppression.cpp'))[0]
        [checker.run_check(config) for checker in self.checkers]
        tokens = config.tokenlist
        assignment_wrap_report_error.assert_has_calls(
            [
                self.assignment_wrap_called(tokens, 275),
                self.assignment_wrap_called(tokens, 319),
                self.assignment_wrap_called(tokens, 663),
            ],
            any_order=True,
        )
        case_separator_report_error.assert_has_calls(
            [
                self.case_separator_called(tokens, "'*'", 66),
                self.case_separator_called(tokens, 'Result', 444),
                self.case_separator_called(tokens, 'Result', 446),
                self.case_separator_called(tokens, 'State', 839),
                self.case_separator_called(tokens, 'State', 845),
                self.case_separator_called(tokens, 'State', 852),
            ],
            any_order=True,
        )
        complicated_assignment_report_error.assert_has_calls(
            [
                self.complicated_assignment_called(tokens, "=", 356),
                self.complicated_assignment_called(tokens, "=", 758),
                self.complicated_assignment_called(tokens, "=", 761),
            ],
            any_order=True,
        )
        duplicate_blank_line_report_error.assert_has_calls(
            [
                self.duplicate_blank_line_called(tokens, '}', 900, 1, 109),
                self.duplicate_blank_line_called(tokens, '}', 900, 1, 733),
                self.duplicate_blank_line_called(tokens, '}', 900, 1, 808),
                self.duplicate_blank_line_called(tokens, '}', 900, 1, 892),
            ],
            any_order=True,
        )
        duplicate_whitespace_report_error.assert_has_calls(
            [
                self.duplicate_whitespace_called(tokens, '}', 900, 1, 12, 56),
                self.duplicate_whitespace_called(tokens, '}', 900, 1, 16, 28),
                self.duplicate_whitespace_called(tokens, '}', 900, 1, 32, 18),
            ],
        )
        identifier_name_report_error.assert_has_calls(
            [
                self.identifier_name_called(
                    tokens,
                    'isAcceptedErrorIdChar',
                    'is_accepted_error_id_char',
                    60,
                    13,
                ),
                self.identifier_name_called(
                    tokens,
                    'symbolNameString',
                    'symbol_name_string',
                    183,
                    27,
                ),
                self.identifier_name_called(tokens, 'lineStream', 'line_stream', 210, 24),
                self.identifier_name_called(
                    tokens,
                    'foundSuppression',
                    'found_suppression',
                    275,
                    10,
                ),
                self.identifier_name_called(tokens, 'newSuppression', 'new_suppression', 306, 16),
                self.identifier_name_called(
                    tokens,
                    'foundSuppression',
                    'found_suppression',
                    319,
                    10,
                ),
                self.identifier_name_called(tokens, 'extraPos', 'extra_pos', 345, 28),
                self.identifier_name_called(
                    tokens,
                    'extraDelimiterSize',
                    'extra_delimiter_size',
                    346,
                    28,
                ),
                self.identifier_name_called(
                    tokens,
                    'symbolNameString',
                    'symbol_name_string',
                    378,
                    23,
                ),
                self.identifier_name_called(tokens, 'matchedSymbol', 'matched_symbol', 419, 14),
                self.identifier_name_called(
                    tokens,
                    'unmatchedSuppression',
                    'unmatched_suppression',
                    461,
                    16,
                ),
                self.identifier_name_called(tokens, 'returnValue', 'return_value', 462, 10),
                self.identifier_name_called(tokens, 'currLineNr', 'curr_line_nr', 619, 9),
                self.identifier_name_called(tokens, 'currFileIdx', 'curr_file_idx', 620, 9),
                self.identifier_name_called(tokens, 'haveMisraAddon', 'have_misra_addon', 663, 16),
                self.identifier_name_called(tokens, 'matchArg', 'match_arg', 674, 16),
                self.identifier_name_called(tokens, 'prevChar', 'prev_char', 681, 20),
                self.identifier_name_called(tokens, 'nextChar', 'next_char', 682, 20),
                self.identifier_name_called(tokens, 'polyspaceKind', 'polyspace_kind', 746, 20),
                self.identifier_name_called(tokens, 'rangeValue', 'range_value', 751, 19),
                self.identifier_name_called(tokens, 'extraComment', 'extra_comment', 767, 21),
                self.identifier_name_called(tokens, 'errorId', 'error_id', 776, 33),
            ],
            any_order=True,
        )
        illegal_catch_report_error.assert_has_calls(
            [self.illegal_catch_called(tokens, 240)],
        )
        import_order_report_error.assert_has_calls(
            [
                self.import_order_called(
                    tokens,
                    '}',
                    900,
                    1,
                    "Arrange directive 'errorlogger.h' before 'suppressions.h'.",
                    21,
                ),
                self.import_order_called(
                    tokens,
                    '}',
                    900,
                    1,
                    "Remove blank line before directive 'errorlogger.h'.",
                    21,
                ),
                self.import_order_called(
                    tokens,
                    '}',
                    900,
                    1,
                    "Arrange directive 'token.h' before 'utils.h'.",
                    26,
                ),
                self.import_order_called(
                    tokens,
                    '}',
                    900,
                    1,
                    "Arrange directive 'settings.h' before 'tokenlist.h'.",
                    29,
                ),
                self.import_order_called(
                    tokens,
                    '}',
                    900,
                    1,
                    "Arrange directive 'algorithm' before 'settings.h'.",
                    31,
                ),
                self.import_order_called(
                    tokens,
                    '}',
                    900,
                    1,
                    "Remove blank line before directive 'algorithm'.",
                    31,
                ),
                self.import_order_called(
                    tokens,
                    '}',
                    900,
                    1,
                    "Remove blank line before directive 'xml.h'.",
                    38,
                ),
            ],
            any_order=True,
        )
        indent_style_report_error.assert_has_calls(
            [
                self.indent_style_called(tokens, 'std', 276),
                self.indent_style_called(tokens, 'std', 320),
            ],
            any_order=True,
        )
        line_length_report_error.assert_has_calls(
            [
                self.line_length_called(tokens, 42),
                self.line_length_called(tokens, 115),
                self.line_length_called(tokens, 119),
                self.line_length_called(tokens, 121),
                self.line_length_called(tokens, 123),
                self.line_length_called(tokens, 126),
                self.line_length_called(tokens, 140),
                self.line_length_called(tokens, 151),
                self.line_length_called(tokens, 160),
                self.line_length_called(tokens, 178),
                self.line_length_called(tokens, 196),
                self.line_length_called(tokens, 276),
                self.line_length_called(tokens, 320),
                self.line_length_called(tokens, 389),
                self.line_length_called(tokens, 394),
                self.line_length_called(tokens, 404),
                self.line_length_called(tokens, 415),
                self.line_length_called(tokens, 474),
                self.line_length_called(tokens, 489),
                self.line_length_called(tokens, 506),
                self.line_length_called(tokens, 543),
                self.line_length_called(tokens, 627),
                self.line_length_called(tokens, 631),
                self.line_length_called(tokens, 634),
                self.line_length_called(tokens, 712),
                self.line_length_called(tokens, 734),
                self.line_length_called(tokens, 828),
                self.line_length_called(tokens, 833),
                self.line_length_called(tokens, 882),
            ],
            any_order=True,
        )
        operator_wrap_report_error.assert_has_calls(
            [self.operator_wrap_called(tokens, '||', 705, 37, True)],
        )
        parameter_wrap_report_error.assert_has_calls(
            [
                self.parameter_wrap_called(tokens, 'mSuppressions', 275, 65),
                self.parameter_wrap_called(tokens, 'mSuppressions', 319, 65),
            ],
            any_order=True,
        )
        todo_comment_report_error.assert_has_calls(
            [
                self.todo_comment_called(tokens, '}', 900, 1, "Omit separator ':'.", 120),
                self.todo_comment_called(tokens, '}', 900, 1, "Omit separator ':'.", 597),
            ],
            any_order=True,
        )


if __name__ == '__main__':
    main()
