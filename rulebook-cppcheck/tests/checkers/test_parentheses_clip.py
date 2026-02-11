from unittest import main
from unittest.mock import patch, call

from rulebook_cppcheck.checkers.parentheses_clip import ParenthesesClipChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestParenthesesClipChecker(CheckerTestCase):
    CHECKER_CLASS = ParenthesesClipChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(ParenthesesClipChecker, 'report_error')
    def test_wrapped_parentheses(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                void foo() {
                    bar();
                }
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(ParenthesesClipChecker, 'report_error')
    def test_unwrapped_parentheses(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                void foo(
                ) {
                    bar(

                    );
                }
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == '(' and t.linenr == 2),
                    _Messages.get(self.checker.MSG, '()'),
                ),
                call(
                    next(t for t in tokens if t.str == '(' and t.linenr == 4),
                    _Messages.get(self.checker.MSG, '()'),
                ),
            ],
            any_order=True,
        )


if __name__ == '__main__':
    main()
