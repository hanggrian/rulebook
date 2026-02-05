from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.duplicate_blank_line_in_comment import \
    DuplicateBlankLineInCommentChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestDuplicateBlankLineInCommentChecker(CheckerTestCase):
    CHECKER_CLASS = DuplicateBlankLineInCommentChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(DuplicateBlankLineInCommentChecker, 'report_error')
    def test_single_empty_line_in_eol_comment(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            void foo() {
                // Lorem ipsum
                //
                // dolor sit amet.
            }
            ''',
        )
        mock_report.assert_not_called()

    @patch.object(DuplicateBlankLineInCommentChecker, 'report_error')
    def test_multiple_empty_lines_in_eol_comment(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            void foo() {
                // Lorem ipsum
                //
                //
                // dolor sit amet.
            }
            ''',
        )
        self.assertEqual(mock_report.call_count, 1)
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))
        self.assertEqual(args[2], 5)


if __name__ == '__main__':
    main()
