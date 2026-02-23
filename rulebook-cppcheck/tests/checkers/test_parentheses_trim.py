from textwrap import dedent
from unittest import main
from unittest.mock import call, patch

from rulebook_cppcheck.checkers.parentheses_trim import ParenthesesTrimChecker
from ..tests import CheckerTestCase, assert_properties


class TestParenthesesTrimChecker(CheckerTestCase):
    CHECKER_CLASS = ParenthesesTrimChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(ParenthesesTrimChecker, 'report_error')
    def test_parentheses_without_newline_padding(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo(
                        int bar
                    ) {
                        printf(
                            "%d", bar
                        );
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(ParenthesesTrimChecker, 'report_error')
    def test_parentheses_with_newline_padding(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo(

                        int bar

                    ) {
                        printf(

                            "%d", bar

                        );
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == '(' and t.linenr == 2),
                    "Remove blank line after '('.",
                    3,
                ),
                call(
                    next(t for t in tokens if t.str == ')' and t.linenr == 6),
                    "Remove blank line before ')'.",
                    5,
                ),
                call(
                    next(t for t in tokens if t.str == '(' and t.linenr == 7),
                    "Remove blank line after '('.",
                    8,
                ),
                call(
                    next(t for t in tokens if t.str == ')' and t.linenr == 11),
                    "Remove blank line before ')'.",
                    10,
                ),
            ],
        )

    @patch.object(ParenthesesTrimChecker, 'report_error')
    def test_comments_are_considered_content(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo(
                        // Lorem
                        int bar
                        // ipsum.
                    ) {
                        printf(
                            // Lorem
                            "%d", bar
                            // ipsum.
                        );
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()


if __name__ == '__main__':
    main()
