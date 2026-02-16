from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.duplicate_blank_line_in_comment import \
    DuplicateBlankLineInCommentChecker
from ..tests import assert_properties, CheckerTestCase


class TestDuplicateBlankLineInCommentChecker(CheckerTestCase):
    CHECKER_CLASS = DuplicateBlankLineInCommentChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(DuplicateBlankLineInCommentChecker, 'report_error')
    def test_single_empty_line_in_eol_comment(self, report_error):
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
        report_error.assert_not_called()

    @patch.object(DuplicateBlankLineInCommentChecker, 'report_error')
    def test_multiple_empty_lines_in_eol_comment(self, report_error):
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
        self.assertEqual(report_error.call_count, 1)
        args, _ = report_error.call_args
        self.assertEqual(args[1], "Remove consecutive blank line after '//'.")
        self.assertEqual(args[2], 5)


if __name__ == '__main__':
    main()
