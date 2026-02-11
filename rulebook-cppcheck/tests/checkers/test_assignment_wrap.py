from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.assignment_wrap import AssignmentWrapChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestAssignmentWrapChecker(CheckerTestCase):
    CHECKER_CLASS = AssignmentWrapChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_single_line_assignment(self, mock_report):
        [
            self.checker.process_token(token)
            for token in self.dump_tokens('void foo() { int bar = 1 + 2; }')
        ]
        mock_report.assert_not_called()

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_multiline_assignment_with_breaking_assignee(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                void foo() {
                    int bar =
                        1 + 2;
                }
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_multiline_assignment_with_non_breaking_assignee(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                void foo() {
                    int bar = 1 +
                        2;
                }
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_called_once_with(
            next(t for t in tokens if t.str == '='),
            _Messages.get(self.checker.MSG),
        )

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_multiline_variable_but_single_line_value(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                void foo(Bar bar) {
                    bar
                        .baz = 1;
                }
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_allow_comments_after_assign_operator(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
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
        ]
        mock_report.assert_not_called()

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_skip_lambda_initializers(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                void foo() {
                    auto bar = [](int a) {
                        cout << a;
                    };
                    auto baz = [](int a) cout << a;
                }
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_skip_collection_initializers(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
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
        ]
        mock_report.assert_not_called()


if __name__ == '__main__':
    main()
