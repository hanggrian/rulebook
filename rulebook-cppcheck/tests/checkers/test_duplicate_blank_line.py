from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.duplicate_blank_line import DuplicateBlankLineChecker
from ..tests import assert_properties, CheckerTestCase


class TestDuplicateBlankLineChecker(CheckerTestCase):
    CHECKER_CLASS = DuplicateBlankLineChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(DuplicateBlankLineChecker, 'report_error')
    def test_single_empty_line(self, report_error):
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            void foo() {

            }
            ''',
        )
        report_error.assert_not_called()

    @patch.object(DuplicateBlankLineChecker, 'report_error')
    def test_multiple_empty_lines(self, report_error):
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''

            class Bar {
                void foo() {


                }
            }
            ''',
        )
        self.assertEqual(report_error.call_count, 2)
        calls = report_error.call_args_list
        self.assertEqual(calls[0][0][1], 'Remove consecutive blank line.')
        self.assertEqual(calls[0][0][2], 2)
        self.assertEqual(calls[1][0][1], 'Remove consecutive blank line.')
        self.assertEqual(calls[1][0][2], 6)


if __name__ == '__main__':
    main()
