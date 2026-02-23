from textwrap import dedent
from unittest import main
from unittest.mock import call, patch

from rulebook_cppcheck.checkers.parentheses_clip import ParenthesesClipChecker
from ..tests import CheckerTestCase, assert_properties


class TestParenthesesClipChecker(CheckerTestCase):
    CHECKER_CLASS = ParenthesesClipChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(ParenthesesClipChecker, 'report_error')
    def test_wrapped_parentheses(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo() {
                        bar();
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(ParenthesesClipChecker, 'report_error')
    def test_unwrapped_parentheses(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo(
                    ) {
                        bar(

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
                    "Convert into '()'.",
                ),
                call(
                    next(t for t in tokens if t.str == '(' and t.linenr == 4),
                    "Convert into '()'.",
                ),
            ],
        )

    @patch.object(ParenthesesClipChecker, 'report_error')
    def test_skip_parameter_without_identifier(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo(void) {
                        bar();
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(ParenthesesClipChecker, 'report_error')
    def test_skip_control_flows_with_multi_blocks(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo() {
                        try {
                        } catch (std::exception& e) {
                        }

                        if (true) {
                        } else if (false) {
                        } else {
                        }

                        do {
                        } while (true);
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()


if __name__ == '__main__':
    main()
