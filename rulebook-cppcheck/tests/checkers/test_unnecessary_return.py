from textwrap import dedent
from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers import UnnecessaryReturnChecker
from ..tests import CheckerTestCase, assert_properties


class TestUnnecessaryReturnChecker(CheckerTestCase):
    CHECKER_CLASS = UnnecessaryReturnChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(UnnecessaryReturnChecker, 'report_error')
    def test_function_doesnt_end_with_return(self, report_error):
        _, scopes = \
            self.dump_code(
                dedent(
                    '''
                    void foo() {
                        printf("foo");
                    }
                    ''',
                ),
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_not_called()

    @patch.object(UnnecessaryReturnChecker, 'report_error')
    def test_function_end_with_return(self, report_error):
        tokens, scopes = \
            self.dump_code(
                dedent(
                    '''
                    void foo() {
                        printf("foo");
                        return;
                    }
                    ''',
                ),
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == 'return'),
            "Last 'return' is not needed.",
        )

    @patch.object(UnnecessaryReturnChecker, 'report_error')
    def test_capture_trailing_non_return(self, report_error):
        tokens, scopes = \
            self.dump_code(
                dedent(
                    '''
                    void foo() {
                        printf("foo");
                        return;

                        // Lorem ipsum.
                    }
                    ''',
                ),
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == 'return'),
            "Last 'return' is not needed.",
        )

    @patch.object(UnnecessaryReturnChecker, 'report_error')
    def test_skip_return_statement_with_value(self, report_error):
        _, scopes = \
            self.dump_code(
                dedent(
                    '''
                    int foo() {
                        printf("foo");
                        return 0;
                    }
                    ''',
                ),
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_not_called()


if __name__ == '__main__':
    main()
