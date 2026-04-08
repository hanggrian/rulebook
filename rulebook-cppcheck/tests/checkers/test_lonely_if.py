from textwrap import dedent
from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers import LonelyIfChecker
from .checker_case import CheckerTestCase
from ..asserts import assert_properties


class TestLonelyIfChecker(CheckerTestCase):
    CHECKER_CLASS = LonelyIfChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(LonelyIfChecker, 'report_error')
    def test_if_not_in_else_block(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo() {
                        if (true) {
                            printf("");
                        } else if (false) {
                            printf("");
                        }
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(LonelyIfChecker, 'report_error')
    def test_if_in_else_block(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo() {
                        if (true) {
                            printf("");
                        } else {
                            if (false) {
                                printf("");
                            }
                        }
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == 'if' and t.linenr == 6),
            "Replace 'else' with 'else if' condition.",
        )

    @patch.object(LonelyIfChecker, 'report_error')
    def test_capture_trailing_non_ifs(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo() {
                        if (true) {
                            printf("");
                        } else {
                            if (false) {
                                printf("");
                            }

                            // Lorem ipsum.
                        }
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == 'if' and t.linenr == 6),
            "Replace 'else' with 'else if' condition.",
        )


if __name__ == '__main__':
    main()
