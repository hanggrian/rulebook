from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.duplicate_blank_line import DuplicateBlankLineChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestDuplicateBlankLineChecker(CheckerTestCase):
    CHECKER_CLASS = DuplicateBlankLineChecker

    @patch.object(DuplicateBlankLineChecker, 'report_error')
    def test_single_empty_line(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            void foo() {

            }
            ''',
        )
        mock_report.assert_not_called()

    @patch.object(DuplicateBlankLineChecker, 'report_error')
    def test_multiple_empty_lines(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''

            class Bar {
                void foo() {


                }
            }
            ''',
        )
        self.assertEqual(mock_report.call_count, 2)
        calls = mock_report.call_args_list
        msg = _Messages.get(self.checker.MSG)
        self.assertEqual(calls[0][0][1], msg)
        self.assertEqual(calls[0][0][2], 2)
        self.assertEqual(calls[1][0][1], msg)
        self.assertEqual(calls[1][0][2], 6)


if __name__ == '__main__':
    main()
