from unittest import main
from unittest.mock import patch, call

from rulebook_cppcheck.checkers.parentheses_trim import ParenthesesTrimChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestParenthesesTrimChecker(CheckerTestCase):
    CHECKER_CLASS = ParenthesesTrimChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(ParenthesesTrimChecker, 'report_error')
    def test_parentheses_without_newline_padding(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                void foo(
                    int bar
                ) {
                    printf(
                        "%d", bar
                    );
                }
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(ParenthesesTrimChecker, 'report_error')
    def test_parentheses_with_newline_padding(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                void foo(

                    int bar

                ) {
                    printf(

                        "%d", bar

                    );
                }
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == '(' and t.linenr == 2),
                    _Messages.get(self.checker.MSG_FIRST, '('),
                    3,
                ),
                call(
                    next(t for t in tokens if t.str == ')' and t.linenr == 6),
                    _Messages.get(self.checker.MSG_LAST, ')'),
                    5,
                ),
                call(
                    next(t for t in tokens if t.str == '(' and t.linenr == 7),
                    _Messages.get(self.checker.MSG_FIRST, '('),
                    8,
                ),
                call(
                    next(t for t in tokens if t.str == ')' and t.linenr == 11),
                    _Messages.get(self.checker.MSG_LAST, ')'),
                    10,
                ),
            ],
            any_order=True,
        )

    @patch.object(ParenthesesTrimChecker, 'report_error')
    def test_comments_are_considered_content(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
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
            )
        ]
        mock_report.assert_not_called()


if __name__ == '__main__':
    main()
