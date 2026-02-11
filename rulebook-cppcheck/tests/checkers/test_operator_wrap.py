from unittest import main
from unittest.mock import patch, call

from rulebook_cppcheck.checkers.operator_wrap import OperatorWrapChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestOperatorWrapChecker(CheckerTestCase):
    CHECKER_CLASS = OperatorWrapChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(OperatorWrapChecker, 'report_error')
    def test_operators_in_single_line_statement(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                void foo() {
                    int bar = 1 * 2;
                    cout << (3 + 4 - 5);
                }
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(OperatorWrapChecker, 'report_error')
    def test_nl_wrapped_operators_in_multi_line_statement(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                void foo() {
                    int bar =
                        1
                            * 2;
                    cout << 3
                            + 4
                            - 5;
                }
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == '*'),
                    _Messages.get(self.checker.MSG_UNEXPECTED, '*'),
                ),
                call(
                    next(t for t in tokens if t.str == '+'),
                    _Messages.get(self.checker.MSG_UNEXPECTED, '+'),
                ),
                call(
                    next(t for t in tokens if t.str == '-'),
                    _Messages.get(self.checker.MSG_UNEXPECTED, '-'),
                ),
            ],
            any_order=True,
        )

    @patch.object(OperatorWrapChecker, 'report_error')
    def test_eol_wrapped_operators_in_multi_line_statement(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                void foo() {
                    int bar =
                        1 *
                            2;
                    cout <<
                        3 +
                            4 -
                            5;
                }
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(OperatorWrapChecker, 'report_error')
    def test_multiline_operand_need_to_be_wrapped(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                void foo() {
                    int bar =
                        1 * min(
                            2,
                            3
                        );
                    cout <<
                        4 + min(
                            5,
                            6
                        ) - max(
                            7,
                            8
                        );
                }
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == '*'),
                    _Messages.get(self.checker.MSG_MISSING, '*'),
                ),
                call(
                    next(t for t in tokens if t.str == '+'),
                    _Messages.get(self.checker.MSG_MISSING, '+'),
                ),
                call(
                    next(t for t in tokens if t.str == '-'),
                    _Messages.get(self.checker.MSG_MISSING, '-'),
                ),
            ],
            any_order=True,
        )

    @patch.object(OperatorWrapChecker, 'report_error')
    def test_allow_comments_after_operator(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                void foo() {
                    int bar =
                        1 * // Comment
                            2;
                    cout <<
                        3 + /* Short comment */
                            4 -
                            /* Long comment */ 5;
                }
                ''',
            )
        ]
        mock_report.assert_not_called()


if __name__ == '__main__':
    main()
