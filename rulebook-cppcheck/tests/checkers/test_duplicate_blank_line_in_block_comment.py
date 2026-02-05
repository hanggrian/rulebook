from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.duplicate_blank_line_in_block_comment import \
    DuplicateBlankLineInBlockCommentChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestDuplicateBlankLineInBlockCommentChecker(CheckerTestCase):
    CHECKER_CLASS = DuplicateBlankLineInBlockCommentChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(DuplicateBlankLineInBlockCommentChecker, 'report_error')
    def test_single_empty_line_in_block_comment(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            /**
             * Lorem ipsum
             *
             * dolor sit amet.
             */
            void foo() {}
            ''',
        )
        mock_report.assert_not_called()

    @patch.object(DuplicateBlankLineInBlockCommentChecker, 'report_error')
    def test_multiple_empty_lines_in_block_comment(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            /**
             * Lorem ipsum
             *
             *
             * dolor sit amet.
             */
            void foo() {}
            ''',
        )
        self.assertEqual(mock_report.call_count, 1)
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))
        self.assertEqual(args[2], 5)


if __name__ == '__main__':
    main()
