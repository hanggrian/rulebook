from unittest import main
from unittest.mock import call, patch

from rulebook_cppcheck.checkers.case_separator import CaseSeparatorChecker
from ..tests import CheckerTestCase, assert_properties


class TestCaseSeparatorChecker(CheckerTestCase):
    CHECKER_CLASS = CaseSeparatorChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(CaseSeparatorChecker, 'report_error')
    def test_single_line_branches_without_line_break(self, report_error):
        _, scopes = \
            self.dump_code(
                '''
                void foo() {
                    switch (bar) {
                        case 0: baz();
                        case 1: baz(); break;
                        default: baz();
                    }
                }
                ''',
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_not_called()

    @patch.object(CaseSeparatorChecker, 'report_error')
    def test_multiline_branches_with_line_break(self, report_error):
        _, scopes = \
            self.dump_code(
                '''
                void foo() {
                    switch (bar) {
                        case 0:
                            baz();

                        case 1:
                            baz();
                            break;

                        default:
                            baz();
                    }
                }
                ''',
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_not_called()

    @patch.object(CaseSeparatorChecker, 'report_error')
    def test_single_line_branches_with_line_break(self, report_error):
        tokens, scopes = \
            self.dump_code(
                '''
                void foo() {
                    switch (bar) {
                        case 0: baz();

                        case 1: baz(); break;

                        default: baz();
                    }
                }
                ''',
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == '0' and t.linenr == 4),
                    'Remove blank line after single-line branch.',
                ),
                call(
                    next(t for t in tokens if t.str == '1' and t.linenr == 6),
                    'Remove blank line after single-line branch.',
                ),
            ],
        )

    @patch.object(CaseSeparatorChecker, 'report_error')
    def test_multiline_branches_without_line_break(self, report_error):
        tokens, scopes = \
            self.dump_code(
                '''
                void foo() {
                    switch (bar) {
                        case 0:
                            baz();
                        case 1:
                            baz();
                            break;
                        default:
                            baz();
                    }
                }
                ''',
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == '0' and t.linenr == 4),
                    'Add blank line after multiline branch.',
                ),
                call(
                    next(t for t in tokens if t.str == '1' and t.linenr == 6),
                    'Add blank line after multiline branch.',
                ),
            ],
        )

    @patch.object(CaseSeparatorChecker, 'report_error')
    def test_multiple_branches_are_considered_multiline(self, report_error):
        tokens, scopes = \
            self.dump_code(
                '''
                void foo() {
                    switch (bar) {
                        case 0:
                        case 1: baz();
                        case 2: baz();
                    }
                }
                ''',
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == '0' and t.linenr == 4),
            'Add blank line after multiline branch.',
        )

    @patch.object(CaseSeparatorChecker, 'report_error')
    def test_multiple_branches_are_considered_multiline2(self, report_error):
        _, scopes = \
            self.dump_code(
                '''
                void foo() {
                    switch (bar) {
                        case 0:
                        case 1:
                        case 2:
                            switch (baz) {
                                case 0:
                                case 1:
                                case 2:
                                    qux();
                                    qux();
                            }
                    }
                }
                ''',
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_not_called()


if __name__ == '__main__':
    main()
