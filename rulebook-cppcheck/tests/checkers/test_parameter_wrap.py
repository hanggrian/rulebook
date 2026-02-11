from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.parameter_wrap import ParameterWrapChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestParameterWrapChecker(CheckerTestCase):
    CHECKER_CLASS = ParameterWrapChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_single_line_parameters(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                void foo(int a, int b) {}
                void bar() {
                    foo(string("0").c_str(), 0);
                }
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_multiline_parameters_each_with_newline(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                void foo(
                    int a,
                    int b
                ) {}
                void bar() {
                    foo(
                        string("0")
                            .c_str(),
                        0
                    );
                }
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_multiline_parameters_each_without_newline(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                void foo(
                    int a, int b
                ) {}
                void bar() {
                    foo(
                        string("0")
                            .c_str(), 0
                    );
                }
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_called_once_with(
            next(t for t in tokens if t.str == 'int' and t.linenr == 3 and t.column == 28),
            _Messages.get(self.checker.MSG),
        )

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_multiline_parameters_each_hugging_parenthesis(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                void foo(int a,
                        int b) {}
                void bar() {
                    foo(string("0")
                            .c_str(),
                        0);
                }
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_aware_of_chained_single_line_calls(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                void foo() {
                    string("0")
                        .append("Hello", 1, 2);
                }
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_allow_comments_between_parameters(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                void foo(
                    int a,
                    // Comment
                    int b,
                    /* Block comment */
                    int c
                ) {}
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_allow_sam(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                void foo() {
                    execute([]() {
                        bar();
                        bar();
                    });
                }
                ''',
            )
        ]
        mock_report.assert_not_called()


if __name__ == '__main__':
    main()
