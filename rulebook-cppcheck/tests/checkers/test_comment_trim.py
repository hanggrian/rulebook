from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.comment_trim import CommentTrimChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestCommentTrimChecker(CheckerTestCase):
    CHECKER_CLASS = CommentTrimChecker

    @patch.object(CommentTrimChecker, 'report_error')
    def test_comment_without_initial_and_final_newline(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            void foo() {
                // Lorem ipsum.
            }
            ''',
        )
        mock_report.assert_not_called()

    @patch.object(CommentTrimChecker, 'report_error')
    def test_comment_with_initial_and_final_newline(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            void foo() {
                //
                // Lorem ipsum.
                //
            }
            ''',
        )
        self.assertEqual(mock_report.call_count, 2)
        calls = mock_report.call_args_list
        msg = _Messages.get(self.checker.MSG)
        self.assertEqual(calls[0][0][1], msg)
        self.assertEqual(calls[0][0][2], 3)
        self.assertEqual(calls[1][0][1], msg)
        self.assertEqual(calls[1][0][2], 5)

    @patch.object(CommentTrimChecker, 'report_error')
    def test_skip_blank_comment(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            void foo() {

                //

            }
            ''',
        )
        mock_report.assert_not_called()

    @patch.object(CommentTrimChecker, 'report_error')
    def test_skip_comment_with_code(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            void foo() {
                printf(""); //
                printf(""); // Lorem ipsum.
                printf(""); //
            }
            ''',
        )
        mock_report.assert_not_called()


if __name__ == '__main__':
    main()
