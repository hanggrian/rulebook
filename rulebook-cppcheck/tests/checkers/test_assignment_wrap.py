from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.assignment_wrap import AssignmentWrapChecker
from ..tests import CheckerTestCase, assert_properties


class TestAssignmentWrapChecker(CheckerTestCase):
    CHECKER_CLASS = AssignmentWrapChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_single_line_assignment(self, report_error):
        tokens, _ = self.dump_code('void foo() { int bar = 1 + 2; }')
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_multiline_assignment_with_breaking_assignee(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo() {
                    int bar =
                        1 + 2;
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_multiline_assignment_with_non_breaking_assignee(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo() {
                    int bar = 1 +
                        2;
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == '='),
            'Break assignment into newline.',
        )

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_multiline_variable_but_single_line_value(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo(Bar bar) {
                    bar
                        .baz = 1;
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_allow_comments_after_assign_operator(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo() {
                    int bar = // Comment
                        1 + 2;
                    int baz = /* Short comment */
                        1 + 2;
                    int qux = /** Long comment */ 1 + 2;
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_skip_lambda_initializers(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo() {
                    auto bar = [](int a) {
                        cout << a;
                    };
                    auto baz = [](int a) cout << a;
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_skip_collection_initializers(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo() {
                    vector<int> bar = {
                        1,
                        2
                    };
                    map<string, int> baz = {
                        {"a", 1},
                        {"b", 2}
                    };
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()


if __name__ == '__main__':
    main()
