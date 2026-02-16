from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.parameter_wrap import ParameterWrapChecker
from ..tests import assert_properties, CheckerTestCase


class TestParameterWrapChecker(CheckerTestCase):
    CHECKER_CLASS = ParameterWrapChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_single_line_parameters(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo(int a, int b) {}
                void bar() {
                    foo(string("0").c_str(), 0);
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_multiline_parameters_each_with_newline(self, report_error):
        tokens, _ = \
            self.dump_code(
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
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_multiline_parameters_each_without_newline(self, report_error):
        tokens, _ = \
            self.dump_code(
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
        self.checker.process_tokens(tokens)
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == 'int' and t.linenr == 3 and t.column == 28),
            'Break each parameter into newline.',
        )

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_multiline_parameters_each_hugging_parenthesis(self, report_error):
        tokens, _ = \
            self.dump_code(
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
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_aware_of_chained_single_line_calls(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo() {
                    string("0")
                        .append("Hello", 1, 2);
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_allow_comments_between_parameters(self, report_error):
        tokens, _ = \
            self.dump_code(
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
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_allow_sam(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo() {
                    execute([]() {
                        bar();
                        bar();
                    });
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()


if __name__ == '__main__':
    main()
