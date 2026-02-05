from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.comment_space import CommentSpaceChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestCommentSpaceChecker(CheckerTestCase):
    CHECKER_CLASS = CommentSpaceChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(CommentSpaceChecker, 'report_error')
    def test_with_whitespace(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            // Valid comment
            int x = 0; // Valid trailing
            ''',
        )
        mock_report.assert_not_called()

    @patch.object(CommentSpaceChecker, 'report_error')
    def test_without_whitespace(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            int x = 0;
            int y = 0;
            //Invalid 1
            //Invalid 2
            ''',
        )
        self.assertEqual(mock_report.call_count, 2)
        calls = mock_report.call_args_list
        self.assertEqual(calls[0][0][1], _Messages.get(self.checker.MSG))
        self.assertEqual(calls[0][0][2], 4)
        self.assertEqual(calls[0][0][3], 13)
        self.assertEqual(calls[1][0][1], _Messages.get(self.checker.MSG))
        self.assertEqual(calls[1][0][2], 5)
        self.assertEqual(calls[1][0][3], 13)

    @patch.object(CommentSpaceChecker, 'report_error')
    def test_ignore_block_comment(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            /* No space required */
            /**
             * No space required
             */
            ''',
        )
        mock_report.assert_not_called()

    @patch.object(CommentSpaceChecker, 'report_error')
    def test_capture_repeated_slashes_without_content(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            //
            ///
            ////
            // content
            //invalid
            //
            ''',
        )
        self.assertEqual(mock_report.call_count, 1)
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))
        self.assertEqual(args[2], 6)

    @patch.object(CommentSpaceChecker, 'report_error')
    def test_skip_special_comments(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            ////////////////////
            //
            ////////////////////
            ''',
        )
        mock_report.assert_not_called()


if __name__ == '__main__':
    main()
