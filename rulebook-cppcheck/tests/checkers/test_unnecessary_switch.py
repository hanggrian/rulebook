from textwrap import dedent
from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.unnecessary_switch import UnnecessarySwitchChecker
from ..tests import CheckerTestCase, assert_properties


class TestUnnecessarySwitchChecker(CheckerTestCase):
    CHECKER_CLASS = UnnecessarySwitchChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(UnnecessarySwitchChecker, 'report_error')
    def test_multiple_switch_branches(self, report_error):
        _, scopes = \
            self.dump_code(
                dedent(
                    '''
                    void foo() {
                        switch (bar) {
                            case 0:
                                baz()
                                break
                            case 1:
                                baz()
                                break
                        }
                    }
                    ''',
                ),
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_not_called()

    @patch.object(UnnecessarySwitchChecker, 'report_error')
    def test_single_switch_branch(self, report_error):
        tokens, scopes = \
            self.dump_code(
                dedent(
                    '''
                    void foo() {
                        switch (bar) {
                            case 0:
                                baz()
                                break
                        }
                    }
                    ''',
                ),
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == 'switch'),
            "Replace 'switch' with 'if' condition.",
        )

    @patch.object(UnnecessarySwitchChecker, 'report_error')
    def test_skip_single_branch_if_it_has_fall_through_condition(self, report_error):
        _, scopes = \
            self.dump_code(
                dedent(
                    '''
                    void foo() {
                        switch (bar) {
                            case 0:
                            case 1:
                                baz()
                                break
                        }
                    }
                    ''',
                ),
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_not_called()


if __name__ == '__main__':
    main()
