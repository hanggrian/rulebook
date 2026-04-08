from textwrap import dedent
from unittest import main
from unittest.mock import call, patch

from rulebook_cppcheck.checkers import RedundantIfChecker
from .checker_case import CheckerTestCase
from ..asserts import assert_properties


class TestRedundantIfChecker(CheckerTestCase):
    CHECKER_CLASS = RedundantIfChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(RedundantIfChecker, 'report_error')
    def test_if_else_do_not_contain_boolean_constants(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    bool foo() {
                        if (true) {
                            return true;
                        } else {
                            return 1 == 2;
                        }
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(RedundantIfChecker, 'report_error')
    def test_if_else_contain_boolean_constants(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    bool foo() {
                        if (true) {
                            return true;
                        } else {
                            return false;
                        }
                    }

                    bool bar() {
                        if (true) {
                            return true;
                        }
                        return false;
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == 'if' and t.linenr == 3),
                    "Omit redundant 'if' condition.",
                ),
                call(
                    next(t for t in tokens if t.str == 'if' and t.linenr == 11),
                    "Omit redundant 'if' condition.",
                ),
            ],
        )


if __name__ == '__main__':
    main()
