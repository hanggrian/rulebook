from textwrap import dedent
from unittest import main
from unittest.mock import call, patch

from rulebook_cppcheck.checkers import OperatorWrappingChecker
from .checker_case import CheckerTestCase
from ..asserts import assert_properties


# noinspection PyUnresolvedReferences
class TestOperatorWrappingChecker(CheckerTestCase):
    CHECKER_CLASS = OperatorWrappingChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(OperatorWrappingChecker, 'report_error')
    def test_operators_in_single_line_statement(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo() {
                        int bar = 1 * 2;
                        cout << (3 + 4 - 5);
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(OperatorWrappingChecker, 'report_error')
    def test_nl_wrapped_operators_in_multi_line_statement(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
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
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == '*'),
                    "Omit newline before operator '*'.",
                ),
                call(
                    next(t for t in tokens if t.str == '<<'),
                    "Put newline after operator '<<'.",
                ),
                call(
                    next(t for t in tokens if t.str == '+'),
                    "Omit newline before operator '+'.",
                ),
                call(
                    next(t for t in tokens if t.str == '-'),
                    "Omit newline before operator '-'.",
                ),
            ],
        )

    @patch.object(OperatorWrappingChecker, 'report_error')
    def test_eol_wrapped_operators_in_multi_line_statement(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
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
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(OperatorWrappingChecker, 'report_error')
    def test_multiline_operand_need_to_be_wrapped(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
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
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == '*'),
                    "Put newline after operator '*'.",
                ),
                call(
                    next(t for t in tokens if t.str == '+'),
                    "Put newline after operator '+'.",
                ),
                call(
                    next(t for t in tokens if t.str == '-'),
                    "Put newline after operator '-'.",
                ),
            ],
        )

    @patch.object(OperatorWrappingChecker, 'report_error')
    def test_allow_comments_after_operator(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
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
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()


if __name__ == '__main__':
    main()
