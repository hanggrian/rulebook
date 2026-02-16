from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.comment_space import CommentSpaceChecker
from ..tests import assert_properties, CheckerTestCase


class TestCommentSpaceChecker(CheckerTestCase):
    CHECKER_CLASS = CommentSpaceChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(CommentSpaceChecker, 'report_error')
    def test_with_whitespace(self, report_error):
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            // Valid comment
            int x = 0; // Valid trailing
            ''',
        )
        report_error.assert_not_called()

    @patch.object(CommentSpaceChecker, 'report_error')
    def test_without_whitespace(self, report_error):
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            int x = 0;
            int y = 0;
            //Invalid 1
            //Invalid 2
            ''',
        )
        self.assertEqual(report_error.call_count, 2)
        calls = report_error.call_args_list
        self.assertEqual(calls[0][0][1], "Put one space after '//'.")
        self.assertEqual(calls[0][0][2], 4)
        self.assertEqual(calls[0][0][3], 13)
        self.assertEqual(calls[1][0][1], "Put one space after '//'.")
        self.assertEqual(calls[1][0][2], 5)
        self.assertEqual(calls[1][0][3], 13)

    @patch.object(CommentSpaceChecker, 'report_error')
    def test_ignore_block_comment(self, report_error):
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            /* No space required */
            /**
             * No space required
             */
            ''',
        )
        report_error.assert_not_called()

    @patch.object(CommentSpaceChecker, 'report_error')
    def test_capture_repeated_slashes_without_content(self, report_error):
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
        self.assertEqual(report_error.call_count, 1)
        args, _ = report_error.call_args
        self.assertEqual(args[1], "Put one space after '//'.")
        self.assertEqual(args[2], 6)

    @patch.object(CommentSpaceChecker, 'report_error')
    def test_skip_special_comments(self, report_error):
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            ////////////////////
            //
            ////////////////////
            ''',
        )
        report_error.assert_not_called()


if __name__ == '__main__':
    main()
