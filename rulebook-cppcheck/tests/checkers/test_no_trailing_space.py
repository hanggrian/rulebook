from textwrap import dedent
from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers import NoTrailingSpaceChecker
from .checker_case import CheckerTestCase
from ..asserts import assert_properties


# noinspection PyUnresolvedReferences
class TestNoTrailingSpaceChecker(CheckerTestCase):
    CHECKER_CLASS = NoTrailingSpaceChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(NoTrailingSpaceChecker, 'report_error')
    def test_statement_without_trailing_whitespace(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                void foo() {
                    int bar = 0;
                }
                ''',
            ),
        )
        report_error.assert_not_called()

    @patch.object(NoTrailingSpaceChecker, 'report_error')
    def test_statement_with_trailing_whitespace(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                void foo() {
                    int bar = 0;\t
                }
                ''',
            ),
        )
        self.assertEqual(report_error.call_count, 1)
        calls = report_error.call_args_list
        self.assertEqual(calls[0][0][1], 'Trim trailing whitespace.')
        self.assertEqual(calls[0][0][2], 3)


if __name__ == '__main__':
    main()
