from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers import \
    AssignmentWrapChecker, \
    CaseSeparatorChecker, \
    ImportOrderChecker, \
    LineLengthChecker, \
    OperatorWrapChecker
from .all_checkers_case import AllCheckersTestCase
from ..code import get_code


class TestLibtransmission(AllCheckersTestCase):
    @patch.object(AssignmentWrapChecker, 'report_error')
    @patch.object(CaseSeparatorChecker, 'report_error')
    @patch.object(ImportOrderChecker, 'report_error')
    @patch.object(LineLengthChecker, 'report_error')
    @patch.object(OperatorWrapChecker, 'report_error')
    def test_libtransmission_handshake(
        self,
        operator_wrap_report_error,
        line_length_report_error,
        import_order_report_error,
        case_separator_report_error,
        assignment_wrap_report_error,
    ):
        config = self.dump_configs(get_code('cppcheck', 'libtransmission', 'handshake.cc'))[0]
        [checker.run_check(config) for checker in self.checkers]
        tokens = config.tokenlist
        assignment_wrap_report_error.assert_has_calls(
            [self.assignment_wrap_called(tokens, 435)],
        )
        case_separator_report_error.assert_has_calls(
            [
                self.case_separator_called(tokens, 'State', 832),
                self.case_separator_called(tokens, 'State', 834),
                self.case_separator_called(tokens, 'State', 836),
                self.case_separator_called(tokens, 'State', 838),
                self.case_separator_called(tokens, 'State', 840),
                self.case_separator_called(tokens, 'State', 842),
                self.case_separator_called(tokens, 'State', 844),
                self.case_separator_called(tokens, 'State', 848),
                self.case_separator_called(tokens, 'State', 850),
                self.case_separator_called(tokens, 'State', 852),
            ],
            any_order=True,
        )
        import_order_report_error.assert_has_calls(
            [
                self.import_order_called(
                    tokens,
                    '}',
                    886,
                    1,
                    "Arrange directive 'fmt/format.h' before 'utility'.",
                    15,
                ),
                self.import_order_called(
                    tokens,
                    '}',
                    886,
                    1,
                    "Remove blank line before directive 'fmt/format.h'.",
                    15,
                ),
                self.import_order_called(
                    tokens,
                    '}',
                    886,
                    1,
                    "Remove blank line before directive 'libtransmission/bitfield.h'.",
                    17,
                ),
            ],
            any_order=True,
        )
        line_length_report_error.assert_has_calls(
            [
                self.line_length_called(tokens, 64),
                self.line_length_called(tokens, 87),
                self.line_length_called(tokens, 164),
                self.line_length_called(tokens, 188),
                self.line_length_called(tokens, 216),
                self.line_length_called(tokens, 240),
                self.line_length_called(tokens, 259),
                self.line_length_called(tokens, 294),
                self.line_length_called(tokens, 330),
                self.line_length_called(tokens, 340),
                self.line_length_called(tokens, 370),
                self.line_length_called(tokens, 393),
                self.line_length_called(tokens, 412),
                self.line_length_called(tokens, 456),
                self.line_length_called(tokens, 458),
                self.line_length_called(tokens, 516),
                self.line_length_called(tokens, 527),
                self.line_length_called(tokens, 582),
                self.line_length_called(tokens, 634),
                self.line_length_called(tokens, 653),
                self.line_length_called(tokens, 693),
                self.line_length_called(tokens, 765),
                self.line_length_called(tokens, 861),
            ],
            any_order=True,
        )
        operator_wrap_report_error.assert_has_calls(
            [
                self.operator_wrap_called(tokens, '+', 88, 28, True),
                self.operator_wrap_called(tokens, '+', 88, 46, True),
                self.operator_wrap_called(tokens, '+', 436, 23, True),
                self.operator_wrap_called(tokens, '+', 436, 49, True),
            ],
            any_order=True,
        )


if __name__ == '__main__':
    main()
